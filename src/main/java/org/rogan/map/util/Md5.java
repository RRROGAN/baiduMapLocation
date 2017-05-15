package org.rogan.map.util;

import java.io.PrintStream;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5
{
  private static final Logger log = LoggerFactory.getLogger(Md5.class);
  private static final String SALT_VALUE = "@#$%^&*";
  private static MessageDigest digest = null;
  
  public static String md5(String str, String salt) {
	  if (StringUtils.isBlank(str))
	      return null;
	  if (StringUtils.isBlank(salt)) {
		  salt = SALT_VALUE;
	  }
 	    try
	    {
	      if (digest == null) {
	        digest = MessageDigest.getInstance("MD5");
	      }
	      digest.update(salt.getBytes("UTF-8"));
	      digest.update(str.getBytes("UTF-8"));

	      byte[] bytes = digest.digest();
	      int i = 0;
	      StringBuilder sb = new StringBuilder();

	      int offset = 0; for (int len = bytes.length; offset < len; offset++) {
	        i = bytes[offset];
	        if (i < 0)
	          i += 256;
	        else if (i < 16) {
	          sb.append("0");
	        }
	        sb.append(Integer.toHexString(i));
	      }

	      return sb.toString();
	    } catch (Exception e) {
	      log.error(null, e);
	    }return null;
  }

  public static String md5(String str)
  {
	  return md5(str, SALT_VALUE);
  }

  public static void main(String[] args)
  {
    String code = md5("abd");
    System.out.println(code);
    String hexString = Integer.toHexString(-4);
    System.out.println(hexString);
    
    Encoder encoder = Base64.getEncoder();
    byte[] encode = encoder.encode(String.valueOf(System.currentTimeMillis()).getBytes());
    System.out.println(new String(encode).substring(0,6));
  }
}