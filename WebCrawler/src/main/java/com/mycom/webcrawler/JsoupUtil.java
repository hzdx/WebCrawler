package com.mycom.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JsoupUtil {
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
		Document doc = Jsoup.parse(html,baseUrl);
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        for (Element src : media) {
        	String mediaUrl = src.attr("abs:src");
        	if(!mediaUrl.startsWith(baseUrl)) continue;//不访问跨域的链接 todo 对跨域的选项
        	urlHolder.addUrl(StringUtil.transUrl(baseUrl, mediaUrl));
        }
        for (Element link : imports) {
            String importsUrl = link.attr("abs:href");
            if(!importsUrl.startsWith(baseUrl)) continue;
            urlHolder.addUrl(StringUtil.transUrl(baseUrl, importsUrl));
        }

        for (Element link : links) {
            String linkUrl = link.attr("abs:href");
            if(!linkUrl.startsWith(baseUrl)) continue;
            urlHolder.addUrl(StringUtil.transUrl(baseUrl, linkUrl));
        }
	}
	
}
