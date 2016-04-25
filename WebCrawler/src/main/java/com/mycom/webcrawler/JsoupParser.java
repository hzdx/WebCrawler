package com.mycom.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsoupParser {
	private Logger log = LoggerFactory.getLogger(JsoupParser.class);
	public UrlSetHolder urlHolder;

	public UrlSetHolder getUrlHolder() {
		return urlHolder;
	}

	public void setUrlHolder(UrlSetHolder urlHolder) {
		this.urlHolder = urlHolder;
	}

	public static void main(String[] args) {
		String date = "2011年10月2日日";
        String strs[] =date.split("\\D{1,}");//\\D非数字
        for(int i=0;i<strs.length;i++){
        System.out.println(strs[i]);}
	}

	public void parseUrl(String html,String baseUrl){
		Document doc = Jsoup.parse(html);
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        //todo ./css/apache-maven-fluido-1.4.min.css -> https://maven.apache.org/../css/apache-maven-fluido-1.4.min.css
        for (Element src : media) {
        	String mediaUrl = src.attr("src");
        	log.info("-----------------------------------------------");
        	log.info("jsoupParser get orgin url :{}",mediaUrl);
        	//if(!mediaUrl.startsWith(baseUrl)) continue;//不访问跨域的链接 todo 对跨域的选项
        	urlHolder.addUrl(StringUtil.getAbsUrl(baseUrl, mediaUrl));
        	
        }
        for (Element link : imports) {
            String importsUrl = link.attr("href");
            log.info("-----------------------------------------------");
            log.info("jsoupParser get orgin url :{}",importsUrl);
            //if(!importsUrl.startsWith(baseUrl)) continue;
            urlHolder.addUrl(StringUtil.getAbsUrl(baseUrl, importsUrl));
        }

        for (Element link : links) {
            String linkUrl = link.attr("href");
            log.info("-----------------------------------------------");
            log.info("jsoupParser get orgin url :{}",linkUrl);
            //if(!linkUrl.startsWith(baseUrl)) continue;
            urlHolder.addUrl(StringUtil.getAbsUrl(baseUrl, linkUrl));
        }
	}
	
}
