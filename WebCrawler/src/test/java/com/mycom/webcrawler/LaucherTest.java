package com.mycom.webcrawler;

import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.mycom.webcrawler.httpclient.HttpClientWrapper;
import com.mycom.webcrawler.model.Property;
import com.mycom.webcrawler.persistence.CommonDao;

public class LaucherTest {

	@Test
	public void testBreak(){
		int i = 0;
		while(true){
			System.out.println(i++);
			if(i > 10) break;//0-10
		}
	}
	
	@Test
	public void testStartWith() {
		String s = "/abc/p1cd";
		boolean ok = s.startsWith("/abc/p[0-9]+");
		System.out.println(ok);
	}
	
	@Test
	public void testSplit(){
		String str = "3室1厅1卫";
		String str1 = str.substring(str.indexOf(""), str.indexOf("室"));
		System.out.println(str1);
		System.out.println(str.indexOf(""));
	}
	
	@Test
	public void testParse() throws Exception{
		String url = "http://shanghai.anjuke.com/prop/view/A483863319";
		String html = HttpClientWrapper.fetchUrl(url);
		Document doc = Jsoup.parse(html,"UTF-8");
//		Document doc = Jsoup.parse(HttpClientTest.doget(url));
//		//Document doc = Jsoup.connect("http://shanghai.anjuke.com/prop/view/A482253954").get();
		Elements eles = doc.select("#prop_infor .prop-info-box").select(".p_phrase");//.getElementsByClass("p_phrase");
		Element titleEle = doc.getElementsByTag("title").first(); 
		Properties prop = new Properties();
		prop.put("id", url.split("view/")[1]);
		prop.put("title",titleEle.text());
		for (Element src : eles) {
			String field = src.select("dt").text().trim();
			String value = src.select("dd").text().trim();
			System.out.println(field+"..." + value);
			prop.setProperty(field, value);
		}
		
		Property anjukeProp = new Property(prop);
		String sql = "insert into prop_info(id,title,salePrice,downPay"
				+ ",unitPrice,community,position,roomNum"
				+ ",hallNum,toiletNum,acreage,orientation"
				+ ",totalFloor,floor,decoration,type) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		CommonDao.insert(sql, anjukeProp.getId(),anjukeProp.getTitle(),anjukeProp.getSalePrice(),anjukeProp.getDownPay()
				,anjukeProp.getUnitPrice(),anjukeProp.getCommunity(),anjukeProp.getPosition(),anjukeProp.getRoomNum()
				,anjukeProp.getHallNum(),anjukeProp.getToiletNum(),anjukeProp.getAcreage(),anjukeProp.getOrientation()
				,anjukeProp.getTotalFloor(),anjukeProp.getFloor(),anjukeProp.getDecoration(),anjukeProp.getType());
	}

}
