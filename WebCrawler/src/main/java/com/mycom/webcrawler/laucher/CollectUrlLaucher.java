package com.mycom.webcrawler.laucher;

import com.mycom.webcrawler.component.JsoupParser;
import com.mycom.webcrawler.htmlhandler.LinkHandler;
import com.mycom.webcrawler.httpclient.HttpClientHolder;
import com.mycom.webcrawler.httpclient.SimpleHttpClientHolder;
import com.mycom.webcrawler.urlholder.UrlHolder;

public class CollectUrlLaucher {

	public static void main(String[] args) throws Exception {
		String entryUrl = "http://shanghai.anjuke.com/sale/";
		String targetUrlPrefix = "http://shanghai.anjuke.com/prop/view/";
		String html = SimpleHttpClientHolder.fetchUrl(entryUrl);

		// 初始化jsoupParser,urlHolder组件
		JsoupParser jsoupParser = new JsoupParser();
		UrlHolder urlHolder = new UrlHolder();// 存放要解析的url
		urlHolder.addUrl(entryUrl);
		urlHolder.markUrl(entryUrl);

		jsoupParser.setUrlHolder(urlHolder);
		jsoupParser.setPrefixUrl(entryUrl);
		jsoupParser.setPageHandler(new LinkHandler(targetUrlPrefix));
		// 处理链接

		// 开始处理过程
		jsoupParser.parseHtmlOnlyLink(html, entryUrl);
		loops(jsoupParser, urlHolder);
		HttpClientHolder.close();
	}

	public static void loops(JsoupParser jsoupParser, UrlHolder urlHolder) throws Exception {
		for (String url : urlHolder.getUncrawlUrl()) {
			String html = SimpleHttpClientHolder.fetchUrl(url);
			if (html != null) {
				jsoupParser.parseHtml(html, url);
			}
			urlHolder.markUrl(url);
		}

		if (!urlHolder.isCompleted())
			loops(jsoupParser, urlHolder);

	}

}
