package com.mycom.webcrawler.compnent;

import java.net.URI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsoupParser {
	private Logger log = LoggerFactory.getLogger(JsoupParser.class);
	public UrlSetHolder urlHolder;
	private UriFilter filter;
	

	public void setFilter(UriFilter filter) {
		this.filter = filter;
	}

	public void setUrlHolder(UrlSetHolder urlHolder) {
		this.urlHolder = urlHolder;
	}

	public static void main(String[] args) {
		JsoupParser parser = new JsoupParser();
		System.out.println(parser.resolveUrl(
				"http://apache.fayea.com/","https://maven.apache.org/"));
	}

	private String resolveUrl(String url,String baseUrl){
		URI baseUri = URI.create(baseUrl);
		URI finalUri = baseUri.resolve(url);
		return finalUri.toASCIIString();
	}
	
	private void processUrl(String url,String baseUrl){		
		if(filter == null) filter = new UriFilter();
		url = filter.filter(url);
		if(url != null){
	    	log.info("-----------------------------------------------");
	    	log.info("jsoupParser get orgin url :{}",url);
	    	//if(!mediaUrl.startsWith(baseUrl)) continue;
	    	//不访问跨域的链接:对跨域的选项 由urlHolder控制
	    	urlHolder.addUrl(resolveUrl(url,baseUrl));
		}
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
	@Deprecated
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
