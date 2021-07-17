package com.xyz.microservices.withdrawal.util;

import java.util.UUID;

public final class GeneratorUtil {
	
	private GeneratorUtil() {
	    throw new IllegalStateException("Utility class");
	 }
	
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().substring(0,35);
	}
	
	public static String getUUID(int length) {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().substring(0,length);
	}
}
