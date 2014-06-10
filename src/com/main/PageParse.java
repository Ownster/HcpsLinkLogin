package com.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.util.Constants;

public class PageParse {

	private Document doc = null;
	private Connection.Response res = null;
	private HashMap<String, String> params = new HashMap<String, String>();
	String[] Classes = new String[7];

	public void createConnection(String page) {
		try {
			res = Jsoup.connect("https://hcpslink.henrico.k12.va.us/"+page+".aspx").cookies(CookieManager.getCookies()).userAgent(Constants.user_agent).method(Method.GET).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		doc = Jsoup.parse(res.body());
	}

	public void getNotices() {
		createConnection("Welcome");
		Element table = doc.select("table").first();
		Iterator<Element> iterator = table.select("td").iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next().text()+" Date: "+iterator.next().text());
		}
	}
	public void getSchoolInfo() {
		createConnection("Welcome");
		Element table = doc.select("table").get(3);
		Iterator<Element> iterator = table.select("td").iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next().text()+" "+iterator.next().text());
		}
	}
	public void getAttendance() {
		createConnection("FrontOffice");
		Document doc = Jsoup.parse(res.body());
		Element table = doc.select("table").first();
		Iterator<Element> iterator = table.select("td").iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next().text()+" "+iterator.next().text());
		}
	}

	private void getClasses() {
		createConnection("GradesView");
		Element form = doc.select("select").get(1);
		Elements options = form.getElementsByTag("option");
		for (Element option : options) {
			Classes[Integer.valueOf(option.attr("value"))] = option.html();
		}
	}

	public void getGradesPeriod(int i) {
		try {
			getClasses();
			createConnection("GradesView");
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
			params.put("__EVENTTARGET", "ctl00$ContentPlaceHolder1$ClassSelector$ClassSectionList");
			params.put("__EVENTARGUMENT", "");
			params.put("ctl00$ContentPlaceHolder1$ClassSelector$TermList","3");
			params.put("ctl00$ContentPlaceHolder1$ClassSelector$ClassSectionList", String.valueOf(i));

			res = Jsoup.connect("https://hcpslink.henrico.k12.va.us/GradesView.aspx").data(params).cookies(CookieManager.getCookies()).userAgent(Constants.user_agent).method(Method.POST).execute();
			doc = Jsoup.parse(res.body());

			System.out.println("Class Name: "+Classes[i]);
			
			Element table = doc.select("tbody").get(3);
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				System.out.println(tds.get(0).text()+" | "+tds.get(1).text()+" | "+tds.get(2).text()+" | "+tds.get(3).text());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
