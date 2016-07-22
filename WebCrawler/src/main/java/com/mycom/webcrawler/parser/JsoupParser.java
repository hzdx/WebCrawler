package com.mycom.webcrawler.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.webcrawler.htmlhandler.PageHandler;
import com.mycom.webcrawler.model.CollectConfig;
import com.mycom.webcrawler.model.CollectConfig.UrlType;
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
	private List<PageHandler> handlerList = new LinkedList<>();
	/**
	 * url抓取配置
	 */
	private CollectConfig config;
	private boolean handLink;
	private boolean handImport;
	private boolean handMedia;

	private void processUrl(String url, String baseUrl, Document doc) throws Exception {
		url = StringUtil.cleanUrl(url);
		if (url != null) {
			log.debug("-----------------------------------------------");
			log.debug("jsoup get orgin url :{}", url);

			String finalUrl = StringUtil.resolveUrl(url, baseUrl);

			if (config.getPrefix() != null) {
				if (finalUrl.startsWith(config.getPrefix()))
					urlHolder.addUrl(finalUrl);
			}
		}
	}

	private void processLink(Document doc, String baseUrl) throws Exception {
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String url = link.attr("href").trim();
			processUrl(url, baseUrl, doc);
		}
	}

	@Deprecated
	public void parseHtmlOnlyLink(String html, String baseUrl) throws Exception {
		log.info("**********************************************************");
		log.info("parse url :{}", baseUrl);
		Document doc = Jsoup.parse(html);
		for (PageHandler handler : handlerList) {
			handler.process(html, baseUrl, doc);
		} // 实际对页面的处理
		processLink(doc, baseUrl);
	}

	public void parseHtml(String html, String baseUrl) throws Exception {
		log.info("**********************************************************");
		log.info("parse url :{}", baseUrl);

		Document doc = Jsoup.parse(html);
		for (PageHandler handler : handlerList) {
			handler.process(html, baseUrl, doc);
		} // 实际对页面的处理

		if (handMedia)
			processMedia(doc, baseUrl);
		if (handImport)
			processImport(doc, baseUrl);
		if (handLink)
			processLink(doc, baseUrl);

	}

	private void decideHandType() {
		List<UrlType> types = Arrays.asList(config.getTypes());
		handMedia = types.contains(UrlType.MEDIA);
		handImport = types.contains(UrlType.IMPORT);
		handLink = types.contains(UrlType.LINK);
	}

	public void setConfig(CollectConfig config) {
		this.config = config;
		decideHandType();
	}

	public void addPageHandler(PageHandler pageHandler) {
		handlerList.add(pageHandler);
	}

	public void setUrlHolder(AbstractUrlHolder urlHolder) {
		this.urlHolder = urlHolder;
	}

	/**
	 * 处理文件中的多媒体文件
	 */
	private void processMedia(Document doc, String baseUrl) throws Exception {
		Elements media = doc.select("[src]");
		for (Element src : media) {
			processUrl(src.attr("src"), baseUrl, doc);

		}
	}

	/**
	 * 处理导入
	 */
	private void processImport(Document doc, String baseUrl) throws Exception {
		Elements imports = doc.select("link[href]");
		for (Element link : imports) {
			processUrl(link.attr("href"), baseUrl, doc);
		}
	}

}
