package com.mycom.jsoup.test;

import java.io.File;
import java.net.URI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.webcrawler.util.FileUtil;
import com.mycom.webcrawler.util.StringUtil;

public class JsoupTest {
	private Logger log = LoggerFactory.getLogger(FileUtil.class);
	public static void main(String[] args) throws Exception {
        String url = "https://maven.apache.org/";
        new JsoupTest().listUrl(url);
//		String url = "http://shanghai.anjuke.com/prop/view/A482253954";
//		File file = new File("log1.txt");
//		//Document doc = Jsoup.parse(file,"UTF-8");
//		Document doc = Jsoup.parse(HttpClientTest.doget(url));
//		//Document doc = Jsoup.connect("http://shanghai.anjuke.com/prop/view/A482253954").get();
//		Elements eles = doc.select("#prop_infor .prop-info-box").select(".p_phrase");//.getElementsByClass("p_phrase");
//		for (Element src : eles) {
//			String field = src.select("dt").text();
//			String value = src.select("dd").text();
//			System.out.println(field+"..." + value);
//		}
    }

	private String resolveUrl(String url,String baseUrl){
		URI baseUri = URI.create(baseUrl);
		if(!baseUri.getPath().startsWith("/")){
			//baseUri.
		}
		URI finalUri = baseUri.resolve(url);
		return finalUri.toASCIIString();
	}
	
	private void listUrl(String url) throws Exception {
		print("Fetching %s...", url);

        //Document doc = Jsoup.connect(url).get();
        Document doc = Jsoup.parse(HttpClientTest.doget(url));
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        for (Element src : media) {
        	String mediaUrl = src.attr("src");
        	log.info(resolveUrl(mediaUrl,url));
        	
        }
        for (Element link : imports) {
            String importsUrl = link.attr("href");
            log.info(resolveUrl(importsUrl,url));
        }

        for (Element link : links) {
            String linkUrl = link.attr("href");
            log.info(resolveUrl(linkUrl,url));
        }
	}

    private void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}


