package com.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Map;

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
			System.out.println("Serialized HashMap data is saved in hashmap.ser");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
