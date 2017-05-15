package org.rogan.map.util;

import java.util.Base64;
import java.util.Base64.Encoder;

/**
 * 
 * @author Rogan
 * 2017年5月3日10:48:32
 * 根据时间戳生成盐值
 */
public class SaltGen {

	
	public static String getSaltByTime() {
		 Encoder encoder = Base64.getEncoder();
		 byte[] encode = encoder.encode(String.valueOf(System.currentTimeMillis()).getBytes());
		 return new String(encode).substring(11,17);
	}
	
	public static void main(String[] args) {
		
		String salt = SaltGen.getSaltByTime();
		System.out.println(salt);
		/**
		 * MTQ5MzcxNzI0Mzk1MQ==
			MTQ5Mz

		 */
		
	}
}
