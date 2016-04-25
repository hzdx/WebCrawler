package com.mycom.jsoup.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.webcrawler.FileUtil;
import com.mycom.webcrawler.StringUtil;

public class JsoupTest {
	private Logger log = LoggerFactory.getLogger(FileUtil.class);
	public static void main(String[] args) throws Exception {
        String url = "https://maven.apache.org";
        new JsoupTest().listUrl(url);
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
        	log.info("-----------------------------------------------");
        	log.info("jsoupParser get orgin url :{}",mediaUrl);
        	//if(!mediaUrl.startsWith(baseUrl)) continue;//不访问跨域的链接 todo 对跨域的选项
        	//urlHolder.addUrl(StringUtil.getAbsUrl(baseUrl, mediaUrl));
        	
        }
        for (Element link : imports) {
            String importsUrl = link.attr("href");
            log.info("-----------------------------------------------");
            log.info("jsoupParser get orgin url :{}",importsUrl);
            //if(!importsUrl.startsWith(baseUrl)) continue;
            //urlHolder.addUrl(StringUtil.getAbsUrl(baseUrl, importsUrl));
        }

        for (Element link : links) {
            String linkUrl = link.attr("href");
            log.info("-----------------------------------------------");
            log.info("jsoupParser get orgin url :{}",linkUrl);
            //if(!linkUrl.startsWith(baseUrl)) continue;
            //urlHolder.addUrl(StringUtil.getAbsUrl(baseUrl, linkUrl));
        }
        //<a href="javascript:;"> 不会添加为链接
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


