package com.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.util.Constants;

public class Login extends PageParse {

	private String username;
	private String password;
	private String loginurl = "https://hcpslink.henrico.k12.va.us/Login.aspx";
	private HashMap<String, String> params = new HashMap<String, String>();

	public Login(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Login() {
		//Nothing
	}

	private void parseParams() {
		status("Getting Params");
		try {
			Document doc = Jsoup.connect(loginurl).get();
			Elements media = doc.select("[type]");
			for (Element src : media) {
				if (src.tagName().equals("input")) {
					String name = src.attr("name");
					String paramvalue = src.attr("value");
					if(!name.isEmpty()) {
						params.put(name, paramvalue);
					}
				}
			}
			params.put("Login1$UserName", username);
			params.put("Login1$Password", password);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void login() {
		try{
			parseParams();
			status("Posting...");
			Connection.Response res = Jsoup.connect(loginurl).data(params).userAgent(Constants.user_agent).method(Method.POST).execute();
			String body = res.body();
			if(body.contains("Your login attempt was not successful. Please try again.")) {
				status("Your login attempt was not successful. Please try again.");
				System.exit(0);
			}else if(body.contains("<div id=\"welcome\">")) {
				status("Correct Login");
				CookieManager.setCookies(res.cookies(), true);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}


	}

	private void status(String string) {
		System.out.println(string);
	}
}
