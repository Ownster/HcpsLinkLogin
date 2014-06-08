package com.main;

import java.util.Map;

public class CookieManager {

	private static Map<String, String> cookies;

	public static Map<String, String> getCookies() {
		return cookies;
	}

	public static void setCookies(Map<String, String> i) {
		cookies = i;
	}

}
