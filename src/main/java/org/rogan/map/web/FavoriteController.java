package org.rogan.map.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.rogan.map.base.BaseController;
import org.rogan.map.entity.Favorite;
import org.rogan.map.entity.ResponseMsg;
import org.rogan.map.entity.User;
import org.rogan.map.serviceImpl.FavoriteServiceImpl;
import org.rogan.map.util.RandomLngLat;
import org.rogan.map.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 
 * @author Rogan
 * 2017年5月6日22:10:43
 */
@Controller
@RequestMapping("/favorite")
public class FavoriteController extends BaseController{
	
	@Autowired
	FavoriteServiceImpl favoriteService;

	@RequestMapping("/collectPoint")
	@ResponseBody
	public ResponseMsg collectPoint(HttpServletRequest request, @RequestParam("start_posi") String start_posi,
			@RequestParam("start_detail_posi") String start_detail_posi,
			@RequestParam("start_lat") String start_lat,
			@RequestParam("start_lng") String start_lng) {
			User user = (User)request.getSession().getAttribute("user");
			if (user == null) {
				return ResponseMsg.err("用户未登录！无法收藏");
			}
			Favorite f = null;
			try {
				 f = favoriteService.collectPoint(user.getUid(), start_posi,start_detail_posi,start_lat,start_lng);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				return ResponseMsg.err(e.getMessage());
			}
			return ResponseMsg.ok("收藏成功", f);
	}
	
	@RequestMapping("/collectRoute")
	public ResponseMsg collectRoute() {
		return null;
	}
	
	@RequestMapping("/del") 
	@ResponseBody
	public ResponseMsg cancelFavorite(String id) {
		try {
			favoriteService.cancelFavorite(id);
			return ResponseMsg.ok("删除成功",null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseMsg.err(e.getMessage());
		}
	}
	@RequestMapping("/rename")
	@ResponseBody
	public ResponseMsg rename(String id, String newname) {
		try {
			//解决中文乱码问题
			newname = new String(newname.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			 favoriteService.rename(id, newname);
			return ResponseMsg.ok("重命名成功",null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseMsg.err(e.getMessage());
		}
	}
	@RequestMapping("/addLabel")
	public ResponseMsg addLabel(String id) {
		return null;
	}
	
	@RequestMapping("/listFavorite")
	@ResponseBody
	public ResponseMsg listFavorite(HttpServletRequest request, String uid) {
		if (StringUtils.isBlank(uid)) {
			return null; 
		}
		try {
			List<Favorite> list = favoriteService.listFavorite(uid);
			HttpSession session = request.getSession();
			session.removeAttribute("favList");
			session.setAttribute("favList",list);
			for (Favorite favorite : list) {
				System.out.println(favorite.getStart_detail_posi());
			}
			return ResponseMsg.ok("" ,list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseMsg.err("收藏列表查询出错");
		}
	
	}
	//在给定区域内随机生成经纬度
	@RequestMapping("/locate")
	@ResponseBody
	public ResponseMsg locate(HttpServletRequest request,
			@RequestParam("lng1") String lng1,
			@RequestParam("lng2") String lng2,
			@RequestParam("lat1") String lat1,
			@RequestParam("lat2") String lat2) {
		Random r = new Random();
		int temp = r.nextInt(20);
		Map<String, Object> map = new HashMap<String, Object>();
		List<String[]> list = new ArrayList<String[]>();
		String[] array = null;
		try {
			for (int i = 0; i < temp; i++) {
				array = RandomLngLat.randomLngLat(lng1, lng2, lat1, lat2);
				list.add(array);
			}
			map.put("count", temp);
			map.put("data", list);
			return ResponseMsg.ok("", map);
		} catch (Exception e) {
			return ResponseMsg.err(e.getMessage());
		}
		
	}	
}
