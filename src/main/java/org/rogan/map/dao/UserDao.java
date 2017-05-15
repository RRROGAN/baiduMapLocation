package org.rogan.map.dao;

import org.apache.ibatis.annotations.Param;
import org.rogan.map.entity.User;
import org.springframework.stereotype.Repository;


/**
 * 
 * @author Rogan
 *  2017Äê5ÔÂ2ÈÕ16:52:43
 */
@Repository
public interface UserDao
{

  public User getUserByNameAndPwd(@Param("login_name") String name, @Param("password") String passwd);

  public User getUserByName(String username);

  public void add(User user);

  public void updatePasswd(User user);
  
  public User getUserByEmail(String email);
  
  public User getUserByPhone(String phone);

  public void updateUser(User user);

}