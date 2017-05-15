package org.rogan.map.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.rogan.map.dao.FavoriteDao;
import org.rogan.map.entity.Favorite;
import org.rogan.map.exception.ServiceException;
import org.rogan.map.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.serializer.MapSerializer;
import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

@Service
public class FavoriteServiceImpl {

	@Autowired
	FavoriteDao favoriteDao;
	
	public Favorite collectPoint(String uid,String start_posi, String start_detail_posi, String start_lat, String start_lng) {
		Favorite point = new Favorite(uid, start_posi, start_detail_posi,
				Double.parseDouble(start_lat), Double.parseDouble(start_lng));
		point.setCollect_type('1');
		point.setCreate_time(DateUtils.getSysTimestamp());
		point.setId(UUID.randomUUID().toString());
		point.setIsfavorite('1'); // 1表示收藏，0表示取消收藏
		try {
			favoriteDao.savePoint(point);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return point;
		
	}

	public List<Favorite> listFavorite(String uid) {
		List<Favorite> list = favoriteDao.getFavoriteByUid(uid);
		return list;
	}

	public int rename(String id, String newname) {
		return favoriteDao.rename(id, newname);
	}

	public int cancelFavorite(String id) {
		return favoriteDao.cancelFavorite(id);
	}

	
	
	
}
