package org.rogan.map.test;

import java.util.UUID;

import org.junit.Test;

public class Test2 {

	@Test
	public void testUUID() {
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid.substring(0, 8));
	}
}
