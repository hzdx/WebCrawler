package com.mycom.webcrawler.laucher;

import com.mycom.webcrawler.compnent.JsoupParser;
import com.mycom.webcrawler.httpclient.HttpClientHolder;
import com.mycom.webcrawler.model.UrlWrapper;
import com.mycom.webcrawler.urlholder.UrlHolder;
import com.mycom.webcrawler.util.FileUtil;

public class WebCrawlerLaucher {
	public static int count = 0;

	public static void main(String[] args) throws Exception {
		String entryUrl = "https://jsoup.org/";
		String outputDir = "d:/crawler/jsoup/";
		String html = HttpClientHolder.fetchUrl(entryUrl).getHtml();
		FileUtil.saveToLocal(html, outputDir, "index.html");
		count++;

		// 初始化jsoupParser,urlHolder组件
		JsoupParser jsoupParser = new JsoupParser();
		UrlHolder urlHolder = new UrlHolder();
		urlHolder.addUrl(entryUrl);
		urlHolder.markUrl(entryUrl);
		jsoupParser.setUrlHolder(urlHolder);
		jsoupParser.setPrefixUrl(entryUrl);

		// 开始处理过程
		jsoupParser.parseHtml(html, entryUrl);
		loops(jsoupParser, urlHolder, entryUrl, outputDir);
		HttpClientHolder.close();
		System.out.println("total save file num :" + count);
	}

	public static void loops(JsoupParser jsoupParser, UrlHolder urlHolder, String entryUrl, String outputDir)
			throws Exception {
		for (String url : urlHolder.getUncrawlUrl()) {
			UrlWrapper wrapper = HttpClientHolder.fetchUrl(url);
			if (wrapper != null) {
				FileUtil.saveFile(wrapper.getHtml(), wrapper.getUrl().replaceAll(entryUrl, outputDir));
				jsoupParser.parseHtml(wrapper.getHtml(), wrapper.getUrl());
			}
			count++;
			urlHolder.markUrl(url);
		}

		if (!urlHolder.isCompleted())
			loops(jsoupParser, urlHolder, entryUrl, outputDir);

	}

}
