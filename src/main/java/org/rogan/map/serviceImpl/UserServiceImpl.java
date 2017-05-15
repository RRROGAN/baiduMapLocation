package org.rogan.map.serviceImpl;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.rogan.map.base.ConfigConsts;
import org.rogan.map.dao.UserDao;
import org.rogan.map.entity.User;
import org.rogan.map.exception.NameOrPasswordException;
import org.rogan.map.exception.ServiceException;
import org.rogan.map.util.DateUtils;
import org.rogan.map.util.MailTask;
import org.rogan.map.util.Md5;
import org.rogan.map.util.SaltGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author Rogan
 * 2017年5月2日16:51:01
 */

@Service("userService")
public class UserServiceImpl {

	protected Logger logger = LoggerFactory.getLogger(getClass()); 

	  @Autowired
	  UserDao userDao;
	  /**
	   * 
	   * @param username
	   * @param password
	   * @return
	   */
	  public User login(String username, String password) {
		
		if (StringUtils.isBlank(username)) {
			throw new NameOrPasswordException("用户名不能为空");
		}
		if (StringUtils.isBlank(password)) {
			throw new NameOrPasswordException("密码不能为空");
		}
		User user = userDao.getUserByName(username);
		if (user == null) {
			throw new NameOrPasswordException("用户名或密码错误");
		} else if (Md5.md5(password, user.getSalt()).equals(user.getPassword())) {
			return user;
		}
		throw new NameOrPasswordException("密码错误");
	}

	/**
	 * 
	 * @param email
	 * @param phone
	 * @param username
	 * @param passwd
	 * @param passwd2
	 * @return
	 */
	public User register(String email, String phone, String username, String passwd, String passwd2) {
		String emailReg = "^[_a-z 0-9]+@([_a-z 0-9]+\\.)+[a-z 0-9]{2,3}$";
		String passwdReg = "^[a-zA-Z]\\w{5,17}$"; 

		boolean f = false;
		if (StringUtils.isBlank(email) || (f = !email.matches(emailReg))) {
			String msg = f ? "邮箱格式不正确" : "邮箱不能为空";
			throw new ServiceException(msg);
		}
		if (StringUtils.isBlank(phone) || (f = (phone.length() != 11))) {
			String msg = f ? "手机号码格式不正确" : "手机号码不能为空";
			throw new ServiceException(msg);
		}
		
		if (StringUtils.isBlank(username)) {
			throw new NameOrPasswordException("昵称不能为空");
		}
		if (StringUtils.isBlank(passwd)) {
			throw new NameOrPasswordException("密码不能为空");
		}
		if (!passwd.matches(passwdReg)) {
			throw new ServiceException("密码以字母开头，长度在6~18之间，只能包含字符、数字和下划线");
		}
		if (!StringUtils.equals(passwd, passwd2)) {
			throw new ServiceException("两次密码输入不一致");
		}
		User user = userDao.getUserByEmail(email);
		if (user != null) {
			throw new ServiceException("邮箱已存在");
		}
		user = userDao.getUserByPhone(phone);
		if (user != null) {
			throw new ServiceException("手机号已存在");
		}
		String salt = SaltGen.getSaltByTime();
		user = new User(username, Md5.md5(passwd, salt));
		user.setCreate_time(DateUtils.getSysTimestamp());
		user.setEmail(email);
		user.setPhone(phone);
		String uuid = UUID.randomUUID().toString();
		user.setUid(uuid);
		user.setSalt(salt);
		user.setState('1');
		userDao.add(user);
		return user;
		
	}

	/**
	 * 
	 * @param username 用户名（邮箱或手机号）
	 * @param lastPasswd
	 * @param newPasswd
	 */
	public void modifyPasswd(String username, String lastPasswd, String newPasswd) {
		if(username == null || username.trim().isEmpty()){
			throw new ServiceException("用户名不能为空");
		}
		if(lastPasswd == null || lastPasswd.trim().isEmpty()){
			throw new ServiceException("原始密码不能为空");
		}
		if(newPasswd == null || newPasswd.trim().isEmpty()){
			throw new ServiceException("新密码不能为空");
		}
		User user = userDao.getUserByName(username);
		if (user == null) {
			throw new ServiceException("用户名不存在");
		}
		if(Md5.md5(lastPasswd, user.getSalt()).equals(user.getPassword())){
			user.setPassword(Md5.md5(newPasswd, user.getSalt()));
			userDao.updatePasswd(user);
		}else{
			throw new ServiceException("原始密码有误");
		}
	}

	/**
	 * 
	 * @param username
	 */
	public String forgetPasswd(String username) {
		if (StringUtils.isBlank(username)) {
			throw new ServiceException("请输入用户名");
		}
		User user = userDao.getUserByName(username);
		if (user == null) {
			throw new ServiceException("用户名不存在，请输入正确的用户名！");
		}
		//随机生成密码
		String pwd = UUID.randomUUID().toString().substring(0, 8);
		pwd = pwd.replace(pwd.charAt(0), 
				ConfigConsts.CHARACTER.charAt((int)(Math.random()*ConfigConsts.CHARACTER.length())));
		user.setPassword(Md5.md5(pwd, user.getSalt()));
		userDao.updatePasswd(user);
		//发送重置密码邮件
		try {
			 ExecutorService exe = Executors.newSingleThreadExecutor();
			 exe.execute(new MailTask(user.getEmail(), user.getLogin_name(),
					  	ConfigConsts.RESETPASSWD_SUBJECT, pwd));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "邮箱地址不存在";
		}
		
		return "您的密码已重置，请注意查看邮件";
	}
	
	public void updateUser(User user) {
		if (user == null) {
			throw new ServiceException("user为空");
		}
		userDao.updateUser(user);
	}
}
