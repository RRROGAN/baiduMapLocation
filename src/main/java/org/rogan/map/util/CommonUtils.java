package org.rogan.map.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author Rogan
 * 2017Äê5ÔÂ3ÈÕ16:33:40
 *
 */
public class CommonUtils {
	public static String readMailTemplate(Class<?> clazz, String path, 
							String nickname, String pwd)
								throws IOException {
		InputStream is = clazz.getClassLoader()
				.getResourceAsStream(path);
	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	String line = "";
	StringBuffer content = new StringBuffer();
	while ((line = br.readLine()) != null ) {
		content.append(line);
	}
	String str = content.toString().replace("#{nickname}", nickname);
	if (StringUtils.isBlank(pwd)) {
		return str;
	}
	return str.replace("#{password}", pwd);
	}
}
