package com.mycom.webcrawler.compnent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.webcrawler.util.StringUtil;


public class JsoupParser {
	private Logger log = LoggerFactory.getLogger(JsoupParser.class);
	private String prefix;
	public UrlSetHolder urlHolder;

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setUrlHolder(UrlSetHolder urlHolder) {
		this.urlHolder = urlHolder;
	}

	public static void main(String[] args) {
	}

	private void processUrl(String url,String baseUrl){
		String link = cleanUrl(url);
    	log.info("-----------------------------------------------");
    	log.info("jsoupParser get orgin url :{}",link);
    	//if(!mediaUrl.startsWith(baseUrl)) continue;//不访问跨域的链接 todo 对跨域的选项
    	urlHolder.addUrl(StringUtil.getAbsUrl(baseUrl, link));
	}
	
	private void processMedia(Document doc,String baseUrl){
		Elements media = doc.select("[src]");      
        for (Element src : media) {
        	processUrl(src.attr("src"),baseUrl);
        	
        }
	}
	private void processLink(Document doc,String baseUrl){
		Elements links = doc.select("a[href]");
        for (Element link : links) {
        	String url = link.attr("href");
        	if(prefix != null){
        		if(!url.startsWith(prefix)) continue;
        	}
        	processUrl(url,baseUrl);
        }
	}
	private void processImport(Document doc,String baseUrl){
		Elements imports = doc.select("link[href]");
        for (Element link : imports) {
            processUrl(link.attr("href"),baseUrl);
        }
	}
	
	public void parseHtmlOnlyLink(String html,String baseUrl){
		log.info("**********************************************************");
		log.info("parse url :{}",baseUrl);
		Document doc = Jsoup.parse(html);    
        processLink(doc,baseUrl);
	}
	
	public void parseHtml(String html,String baseUrl){
		log.info("**********************************************************");
		log.info("parse url :{}",baseUrl);
		Document doc = Jsoup.parse(html);      
        processMedia(doc,baseUrl);
        processImport(doc,baseUrl);
        processLink(doc,baseUrl);
      
	}
	
	/**
	 * 整理url,去掉空格和特殊符号
	 * @param url
	 * @return
	 */
	private String cleanUrl(String url){
		String url1 = url.trim().replace("[","")
				.replace("\"", "").replace("\\", "/");
		if(url1.contains("#")){
			int index = url1.indexOf("#");
			url1 = url1.substring(0, index);
		}
		return url1;
	}
}
