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
 * 2017��5��2��16:51:01
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
			throw new NameOrPasswordException("�û�������Ϊ��");
		}
		if (StringUtils.isBlank(password)) {
			throw new NameOrPasswordException("���벻��Ϊ��");
		}
		User user = userDao.getUserByName(username);
		if (user == null) {
			throw new NameOrPasswordException("�û������������");
		} else if (Md5.md5(password, user.getSalt()).equals(user.getPassword())) {
			return user;
		}
		throw new NameOrPasswordException("�������");
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
			String msg = f ? "�����ʽ����ȷ" : "���䲻��Ϊ��";
			throw new ServiceException(msg);
		}
		if (StringUtils.isBlank(phone) || (f = (phone.length() != 11))) {
			String msg = f ? "�ֻ������ʽ����ȷ" : "�ֻ����벻��Ϊ��";
			throw new ServiceException(msg);
		}
		
		if (StringUtils.isBlank(username)) {
			throw new NameOrPasswordException("�ǳƲ���Ϊ��");
		}
		if (StringUtils.isBlank(passwd)) {
			throw new NameOrPasswordException("���벻��Ϊ��");
		}
		if (!passwd.matches(passwdReg)) {
			throw new ServiceException("��������ĸ��ͷ��������6~18֮�䣬ֻ�ܰ����ַ������ֺ��»���");
		}
		if (!StringUtils.equals(passwd, passwd2)) {
			throw new ServiceException("�����������벻һ��");
		}
		User user = userDao.getUserByEmail(email);
		if (user != null) {
			throw new ServiceException("�����Ѵ���");
		}
		user = userDao.getUserByPhone(phone);
		if (user != null) {
			throw new ServiceException("�ֻ����Ѵ���");
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
	 * @param username �û�����������ֻ��ţ�
	 * @param lastPasswd
	 * @param newPasswd
	 */
	public void modifyPasswd(String username, String lastPasswd, String newPasswd) {
		if(username == null || username.trim().isEmpty()){
			throw new ServiceException("�û�������Ϊ��");
		}
		if(lastPasswd == null || lastPasswd.trim().isEmpty()){
			throw new ServiceException("ԭʼ���벻��Ϊ��");
		}
		if(newPasswd == null || newPasswd.trim().isEmpty()){
			throw new ServiceException("�����벻��Ϊ��");
		}
		User user = userDao.getUserByName(username);
		if (user == null) {
			throw new ServiceException("�û���������");
		}
		if(Md5.md5(lastPasswd, user.getSalt()).equals(user.getPassword())){
			user.setPassword(Md5.md5(newPasswd, user.getSalt()));
			userDao.updatePasswd(user);
		}else{
			throw new ServiceException("ԭʼ��������");
		}
	}

	/**
	 * 
	 * @param username
	 */
	public String forgetPasswd(String username) {
		if (StringUtils.isBlank(username)) {
			throw new ServiceException("�������û���");
		}
		User user = userDao.getUserByName(username);
		if (user == null) {
			throw new ServiceException("�û��������ڣ���������ȷ���û�����");
		}
		//�����������
		String pwd = UUID.randomUUID().toString().substring(0, 8);
		pwd = pwd.replace(pwd.charAt(0), 
				ConfigConsts.CHARACTER.charAt((int)(Math.random()*ConfigConsts.CHARACTER.length())));
		user.setPassword(Md5.md5(pwd, user.getSalt()));
		userDao.updatePasswd(user);
		//�������������ʼ�
		try {
			 ExecutorService exe = Executors.newSingleThreadExecutor();
			 exe.execute(new MailTask(user.getEmail(), user.getLogin_name(),
					  	ConfigConsts.RESETPASSWD_SUBJECT, pwd));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "�����ַ������";
		}
		
		return "�������������ã���ע��鿴�ʼ�";
	}
	
	public void updateUser(User user) {
		if (user == null) {
			throw new ServiceException("userΪ��");
		}
		userDao.updateUser(user);
	}
}
