package com.mycom.webcrawler.laucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mycom.webcrawler.httpclient.SimpleHttpClientHolder;
import com.mycom.webcrawler.model.AnjukeProp;
import com.mycom.webcrawler.persistence.CommonDao;

public class GetPropInfoLaucher {

	public static void main(String[] args) throws Exception {
		List<Object[]> urlList = CommonDao.select("select distinct url from prop_url");
		List<String> urlStrList = new ArrayList<>();
		for (Object[] obj : urlList) {
			urlStrList.add((String) obj[0]);
		}
		final String sql = "insert into prop_info(id,title,salePrice,downPay" + ",unitPrice,community,position,roomNum"
				+ ",hallNum,toiletNum,acreage,orientation" + ",totalFloor,floor,decoration,type) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int threadNum = 8;
		ExecutorService service = Executors.newFixedThreadPool(threadNum);
		int part = urlStrList.size() % threadNum == 0 ? urlStrList.size() / threadNum : urlStrList.size() / threadNum + 1;
		for (int i = 0; i < threadNum; i++) {
			int startIndex = part * i;
			int endIndex = part * (i + 1);
			if (endIndex > urlStrList.size())
				endIndex = urlStrList.size();
			final List<String> subList = urlStrList.subList(startIndex, endIndex);
			Runnable runner = new Runnable() {
				@Override
				public void run() {
					for (String url : subList) {
						try {
							Properties prop = new Properties();
							String html = SimpleHttpClientHolder.fetchUrl(url);
							Document doc = Jsoup.parse(html, "UTF-8");
							Elements eles = doc.select("#prop_infor .prop-info-box").select(".p_phrase");
							Element titleEle = doc.getElementsByTag("title").first();
							prop.put("id", url.split("view/")[1]);
							prop.put("title", titleEle.text());
							for (Element src : eles) {
								String field = src.select("dt").text().trim();
								String value = src.select("dd").text().trim();
								// System.out.println(field+"..." + value);
								prop.setProperty(field, value);
							}
							AnjukeProp anjukeProp = new AnjukeProp(prop);
							CommonDao.insert(sql, anjukeProp.getId(), anjukeProp.getTitle(), anjukeProp.getSalePrice(),
									anjukeProp.getDownPay(), anjukeProp.getUnitPrice(), anjukeProp.getCommunity(),
									anjukeProp.getPosition(), anjukeProp.getRoomNum(), anjukeProp.getHallNum(),
									anjukeProp.getToiletNum(), anjukeProp.getAcreage(), anjukeProp.getOrientation(),
									anjukeProp.getTotalFloor(), anjukeProp.getFloor(), anjukeProp.getDecoration(),
									anjukeProp.getType());
						} catch (Exception e) {
						}

					}
				}

			};
			service.submit(runner);

		}

		service.shutdown();

	}
}
