package org.rogan.map.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.rogan.map.entity.Favorite;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Rogan
 *	2017Äê5ÔÂ6ÈÕ22:10:22
 */
@Repository
public interface FavoriteDao {

	public void savePoint(Favorite point) ;

	public List<Favorite> getFavoriteByUid(String uid);

	public int rename(@Param("id") String id , @Param("newname") String newname);

	public int cancelFavorite(String id);
	
	

}
