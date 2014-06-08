package com.main;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.main.util.Constants;

public class PageParse {

	private Document doc = null;
	private Connection.Response res = null;
	
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
	public void getClassNames() {
		createConnection("GradesView");
		Element table = doc.select("select").get(1);
		Iterator<Element> iterator = table.select("select[id=ctl00_ContentPlaceHolder1_ClassSelector_ClassSectionList]").iterator();
        String classes;
		while(iterator.hasNext()){
			 classes = String.valueOf(iterator.next().text()).replaceAll("\\)","\\)\n");
			 System.out.println(classes);
		}
	}
	public void getGrades() {
		Element table = doc.select("tbody").get(3);
		//Iterator<Element> iterator = table.select("td[class*=grGBPeriodDesc]").iterator();
		Iterator<Element> iterator = table.select("td[class*=grGBPeriodValue]").iterator();
		while(iterator.hasNext()){
			if(!iterator.next().text().isEmpty()) {
			   System.out.println(iterator.next().text().replaceAll("\\s",""));
			}
		}
	}
}
