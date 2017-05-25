package org.rogan.map.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.rogan.map.base.BaseController;
import org.rogan.map.base.ConfigConsts;
import org.rogan.map.entity.ResponseMsg;
import org.rogan.map.entity.User;
import org.rogan.map.serviceImpl.UserServiceImpl;
import org.rogan.map.util.DateUtils;
import org.rogan.map.util.MailTask;
import org.rogan.map.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/user"})
public class UserController extends BaseController
{
  @Resource(name="userService")
  UserServiceImpl userService;

  /**
   * 
   * @param request
   * @param username
   * @param passwd
   * @param verifyCode
   * @return
   */
  @RequestMapping(value={"/login"}, method={RequestMethod.POST})
  @ResponseBody
  public ResponseMsg login(HttpServletRequest request, HttpServletResponse response, 
		  String username, String passwd, String verifyCode) {
	  String userInfo = "username=%s,passwd=%s,verifyCode=%s";
    this.logger.info(String.format(userInfo, new Object[] { username, passwd, verifyCode }));
    HttpSession session = request.getSession();
   boolean isAutoLogin = false;
    try {
    	 Cookie[] cookies = request.getCookies();
    	    for (Cookie cookie : cookies) {
    	    	if ("isAutoLogin".equals(cookie.getName())) {
    	    		isAutoLogin = true;
    	    	}
    			if ("username".equals(cookie.getName())) {
    				username = cookie.getValue();
    			}
    			if ("passwd".equals(cookie.getName())) {
    				passwd = cookie.getValue();
    			}
    		}
    	    if (isAutoLogin == false) {
    	    	ResponseMsg res = check(verifyCode, request);
    	    	if (res == null || !ResponseMsg.OK_CODE.equals(res.getCode())) {
    	    		return res;
    	    	}
    	    }
    	User user = this.userService.login(username, passwd);
    	user.setLast_visit(DateUtils.getSysTimestamp());
    	user.setLogin_count(user.getLogin_count() + 1);
    	userService.updateUser(user);
    	if (request.getParameter("rememberMe") != null) {
    		Cookie cookie_1 = new Cookie("username", username);
    		Cookie cookie_2 = new Cookie("passwd", passwd);
    		Cookie cookie_3 = new Cookie("isAutoLogin", "true");
    		cookie_1.setMaxAge(3600);
    		cookie_2.setMaxAge(3600);
    		cookie_3.setMaxAge(3600);
    		response.addCookie(cookie_1);
    		response.addCookie(cookie_2);
    		response.addCookie(cookie_3);
    	}
    	session.setAttribute("user", user);
    	session.removeAttribute("verifyCode");
    	return ResponseMsg.ok("登录成功", user);
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
    		return ResponseMsg.err(e.getMessage());
	}
  }
  @RequestMapping(value="/register")
  @ResponseBody
  public ResponseMsg register(@RequestParam("email") String email, 
		  					@RequestParam("phone") String phone,
		  					@RequestParam("nickname") String nickname, 
		  					@RequestParam("passwd1") String passwd,
		  					@RequestParam("passwd2") String passwd2) {
	 try {
		  User user = userService.register(email, phone, nickname, passwd, passwd2);
		  //异步发送注册成功邮件
		  ExecutorService exe = Executors.newSingleThreadExecutor();
		  exe.execute(new MailTask(email, nickname,
				  	ConfigConsts.REG_SUBJECT));
		  return ResponseMsg.ok("注册成功", user);
	} catch (Exception e) {
		logger.error(e.getMessage(), e);
		return ResponseMsg.err(e.getMessage());
		}    
  }
  
  /**
   * 
   * @param req
   * @return
   * @throws IOException
   */
  @RequestMapping(value="/code.do",produces="image/png")
	@ResponseBody
	public byte[] code(HttpServletRequest req) throws IOException{
		byte[] buf;
		BufferedImage img = new BufferedImage(80, 30,BufferedImage.TYPE_3BYTE_BGR);
		for(int y=0;y<img.getHeight();y++){
			for(int x=0;x<img.getWidth();x++){
				img.setRGB(x, y, 0xEEEEEE);
			}
		}
		for(int i=0;i<1000;i++){
			int x = (int)(Math.random()*80);
			int y = (int)(Math.random()*30);
			int rgb = (int) (Math.random()*0xffffff);
			img.setRGB(x, y,rgb );
		}
			
			Graphics2D g = img.createGraphics();
			g.setColor(new Color((int) (Math.random()*0xffffff)));
			Font font = new Font(Font.SANS_SERIF, Font.ITALIC, 25);
			g.setFont(font);
		
			int x2 = (int)(Math.random()*10);
			int y2 = (int) (Math.random()*5);
			String code = randomCode();
			req.getSession().setAttribute("code",code);
			
			g.drawString(code, 3 + x2, 28 - y2);
			//将所有的字节放在out流中
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(img, "png", out);
			buf = out.toByteArray();
			return buf;
		
	}
	
	private String randomCode(){
		String chs = "345678ABCDEFGHIJKLXYabcdrph";
		char[] code = new char[4];
		for(int i=0;i<code.length;i++){
			char c = chs.charAt((int)(Math.random()*chs.length()));
			code[i] = c;
		}
		return new String(code);
		
	}
	
	/**
	 * 
	 * @param code
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkCode.do")
	@ResponseBody
	public ResponseMsg check(String code,HttpServletRequest request){
		System.out.println("checkCode:"+code);
		HttpSession session = request.getSession();
		String serverCode = (String)(session.getAttribute("code"));
		if(serverCode == null){
			return ResponseMsg.err("验证码有误");
		}
		if(serverCode.equalsIgnoreCase(code)){
			return ResponseMsg.ok("验证码匹配成功", null);
		}
		return ResponseMsg.err("验证码有误");

	}
	
	/**
	 * 
	 * @param username 手机号或邮箱
	 * @param lastPasswd
	 * @param newPasswd
	 * @param req
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public ResponseMsg modifyPasswd(String username,String old_passwd,String new_passwd,HttpServletRequest req){
		try {
			userService.modifyPasswd(username,old_passwd,new_passwd);
			return ResponseMsg.ok("更改密码成功", null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);;
			return ResponseMsg.err(e.getMessage());
		}
	}
	@RequestMapping("/forget")
	@ResponseBody
	public ResponseMsg forgetPasswd(String username) {
		try {
			String msg = userService.forgetPasswd(username);
			return ResponseMsg.ok(msg, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseMsg.err(e.getMessage());
		}
	}
	@RequestMapping("/logout")
	@ResponseBody
	public ResponseMsg logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if (user != null) {
			session.removeAttribute("user");
		}
	    Cookie[] cookies = request.getCookies();
	    for (Cookie cookie : cookies) {
			System.out.println(cookie.getName() + "=" + cookie.getValue());
		}
		return ResponseMsg.ok("logout success", null);
	}
}