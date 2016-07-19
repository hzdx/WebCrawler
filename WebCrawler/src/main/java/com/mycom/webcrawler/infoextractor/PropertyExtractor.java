package com.mycom.webcrawler.infoextractor;

import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mycom.webcrawler.model.Property;

public class PropertyExtractor implements InfoExtractor<Property> {

	@Override
	public Property extract(String url, String htmlContent) {
		Properties prop = new Properties();
		Document doc = Jsoup.parse(htmlContent, "UTF-8");
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
		Property property = new Property(prop);
		return property;
	}

}
