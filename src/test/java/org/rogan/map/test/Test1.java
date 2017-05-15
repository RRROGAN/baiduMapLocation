package org.rogan.map.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test1 {

	public Test1() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws IOException {
		InputStream is = Test1.class.getClassLoader()
					.getResourceAsStream("mailTemplate/register.template");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = "";
		StringBuffer content = new StringBuffer();
		while ((line = br.readLine()) != null ) {
			content.append(line);
		}
		System.out.println(content.toString());
	}
}
