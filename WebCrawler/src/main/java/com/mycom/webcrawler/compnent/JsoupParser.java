package com.mycom.webcrawler.compnent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.webcrawler.htmlhandler.HtmlHandler;
import com.mycom.webcrawler.htmlhandler.LinkHandler;
import com.mycom.webcrawler.urlholder.AbstractUrlHolder;
import com.mycom.webcrawler.util.StringUtil;

public class JsoupParser {
	private Logger log = LoggerFactory.getLogger(JsoupParser.class);
	/**
	 * 存放解析到的url的容器
	 */
	private AbstractUrlHolder urlHolder;
	/**
	 * 对html文本进行处理的接口对象
	 */
	private HtmlHandler htmlHandler;
	/**
	 * 对存放到urlHolder的url的要求前缀
	 */
	private String prefixUrl;

	private void processUrl(String url, String baseUrl) throws Exception {
		url = StringUtil.cleanUrl(url);
		log.debug("-----------------------------------------------");
		log.debug("jsoup get orgin url :{}", url);
		// 不访问跨域的链接:对跨域的选项 由urlHolder控制
		String finalUrl = StringUtil.resolveUrl(url, baseUrl);
		if(prefixUrl != null){
			if(!finalUrl.startsWith(prefixUrl)) return;
		}		
		urlHolder.addUrl(finalUrl);
		if (htmlHandler != null && htmlHandler instanceof LinkHandler) {
			((LinkHandler) htmlHandler).saveWantedUrl(finalUrl);
		}
	}

	private void processLink(Document doc, String baseUrl) throws Exception {
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String url = link.attr("href").trim();
			if (url.equals("") || url.equals("#") || url.equals("/"))
				continue;// 过滤掉 #,/等链接
			processUrl(url, baseUrl);
		}
	}

	public void parseHtmlOnlyLink(String html, String baseUrl) throws Exception {
		log.info("**********************************************************");
		log.info("parse url :{}", baseUrl);
		Document doc = Jsoup.parse(html);
		processLink(doc, baseUrl);
	}

	public void parseHtml(String html, String baseUrl) throws Exception {
		log.info("**********************************************************");
		log.info("parse url :{}", baseUrl);
		Document doc = Jsoup.parse(html);
		processMedia(doc, baseUrl);
		processImport(doc, baseUrl);
		processLink(doc, baseUrl);

	}
	
	public void setPrefixUrl(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}

	public void setHtmlHandler(HtmlHandler htmlHandler) {
		this.htmlHandler = htmlHandler;
	}

	public void setUrlHolder(AbstractUrlHolder urlHolder) {
		this.urlHolder = urlHolder;
	}

	/**
	 * 处理文件中的多媒体文件
	 * 
	 * @param doc
	 * @param baseUrl
	 * @throws Exception
	 */
	private void processMedia(Document doc, String baseUrl) throws Exception {
		Elements media = doc.select("[src]");
		for (Element src : media) {
			processUrl(src.attr("src"), baseUrl);

		}
	}

	/**
	 * 处理导入
	 * 
	 * @param doc
	 * @param baseUrl
	 * @throws Exception
	 */
	private void processImport(Document doc, String baseUrl) throws Exception {
		Elements imports = doc.select("link[href]");
		for (Element link : imports) {
			processUrl(link.attr("href"), baseUrl);
		}
	}

	
}
