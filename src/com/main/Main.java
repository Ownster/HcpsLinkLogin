package com.main;

import java.io.File;
import java.util.Scanner;


public class Main {

	public static void main(String []Args) {
		try {
			Login user = null;
			if(new File("cookies.ser").exists()) {
				CookieManager.readFile();
				user = new Login();
			}else{
				if(user == null) {
					String username, password = null;
					Scanner scanner = new Scanner(System.in);
					System.out.println("Enter Username:");
					username = scanner.next();
					if(username.isEmpty()) {
						System.out.println("Enter Username:");
						username = scanner.next();
					}
					System.out.println("Enter Password:");
					password = scanner.next();
					if(password.isEmpty()) {
						System.out.println("Enter Password:");
						password = scanner.next();
					}
					user = new Login(username, password);
					scanner.close();
				}
			}
			user.login();
			user.getNotices();
			user.getAttendance();
			user.getClassNames();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}