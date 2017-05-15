package org.rogan.map.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Rogan
 * 2017Äê5ÔÂ3ÈÕ14:45:23
 *
 */
public class ConfigConsts {

	public static final String MAIL_FROM = "375290696@qq.com";
	public static final String AUTHORIZE_CODE = "ciqbitcvksexcaej";
	public static final String CONTENT_TYPE = "text/html;charset=utf-8";
	public static final String REG_SUBJECT = "register";
	public static final String RESETPASSWD_SUBJECT = "resetPasswd";
	public static final String CHARACTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Map<String, String> SUBJECT_MAP = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
	{
		this.put(REG_SUBJECT, "¡¾Congratulation¡¿You have been registered successfully!");
		this.put(RESETPASSWD_SUBJECT,"¡¾Congratulation¡¿Your password has been reset, please modify your password soon!" );
	}};
}
