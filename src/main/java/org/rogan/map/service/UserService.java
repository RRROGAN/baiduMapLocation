package org.rogan.map.service;

import org.rogan.map.entity.User;

public interface UserService
{
	public User login(String username, String password);
	public User register(String username, String passwd, String passwd2) ;

}