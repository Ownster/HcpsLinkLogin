package com.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;

import com.main.util.Constants;

/**
 * This is temporary.
 * @author Ownster
 *
 */

public class CookieManager {

	private static Map<String, String> cookies;

	public static Map<String, String> getCookies() {
		return cookies;
	}

	public static void setCookies(Map<String, String> i, boolean update) {
		cookies = i;
		if(update) {
			updateFile();
		}
	}

	private static void updateFile() {
		try {
			FileOutputStream fos =
					new FileOutputStream("cookies.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(getCookies());
			oos.close();
			fos.close();
		}catch (Exception e) {

		}
	}

	@SuppressWarnings("unchecked")
	public static void readFile() {
		try {
			FileInputStream fis = new FileInputStream("cookies.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			setCookies((Map<String, String>) ois.readObject(), false);
			ois.close();
			fis.close();
			if(checkCookie() == false) {
			   new File("cookies.ser").delete();
			   System.out.println("Cookie invalid");
			   System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static boolean checkCookie() {
		try {
			Connection.Response res = Jsoup.connect("https://hcpslink.henrico.k12.va.us/Welcome.aspx").cookies(getCookies()).userAgent(Constants.user_agent).method(Method.GET).execute();
			if(res.body().contains("User Name:")) {
			   return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
