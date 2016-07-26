package com.codecraft.login;

public class Utils {
	private static final int LENGTH = 16;

	public static synchronized long generateId() {
		long listingIdInnitialDigits = System.currentTimeMillis();
		long appendValueLength = LENGTH - String.valueOf(listingIdInnitialDigits).length();
		long listingIdLastDigits = System.nanoTime() % (long) (Math.pow(10, appendValueLength));
		String id = Long.toString(listingIdInnitialDigits) + Long.toString(listingIdLastDigits);
		return Long.parseLong(id);
	}
}
