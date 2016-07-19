package com.mycom.webcrawler.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.webcrawler.htmlhandler.PageHandler;
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
	private PageHandler pageHandler;
	/**
	 * 对存放到urlHolder的url的前缀要求
	 */
	private String prefixUrl;

	private void processUrl(String url, String baseUrl,Document doc) throws Exception {
		url = StringUtil.cleanUrl(url);
		if (url != null) {
			log.debug("-----------------------------------------------");
			log.debug("jsoup get orgin url :{}", url);

			String finalUrl = StringUtil.resolveUrl(url, baseUrl);
			if (pageHandler != null ) {
				pageHandler.process(null, finalUrl, doc);
			} // 实际对页面的处理
			if (prefixUrl != null) {
				if (finalUrl.startsWith(prefixUrl))
					urlHolder.addUrl(finalUrl);
			}
		}
	}

	private void processLink(Document doc, String baseUrl) throws Exception {
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String url = link.attr("href").trim();
			if (url.equals("") || url.equals("#") || url.equals("/"))
				continue;// 过滤掉 #,/等链接
			processUrl(url, baseUrl,doc);
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

	public void setPageHandler(PageHandler pageHandler) {
		this.pageHandler = pageHandler;
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
			processUrl(src.attr("src"), baseUrl,doc);

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
			processUrl(link.attr("href"), baseUrl,doc);
		}
	}

}
