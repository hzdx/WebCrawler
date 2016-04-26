package com.mycom.webcrawler.laucher;

import com.mycom.webcrawler.compnent.JsoupParser;
import com.mycom.webcrawler.compnent.UrlSetHolder;
import com.mycom.webcrawler.httpclient.HttpClientHolder;
import com.mycom.webcrawler.model.UrlWrapper;
import com.mycom.webcrawler.util.FileUtil;

public class WebCrawlerLaucher {
	public static int count = 0;

	public static void main(String[] args) throws Exception {
		String entryUrl = "http://shanghai.anjuke.com/sale/";
		String outputDir = "d:/crawler/jsoup/";
		String html = HttpClientHolder.fetchUrl(entryUrl).getHtml();
		FileUtil.saveToLocal(html, outputDir, "index.html");
		count++;
		
		//初始化JsoupUtil,urlHolder组件
		JsoupParser jsoupParser = new JsoupParser();
		UrlSetHolder urlHolder = new UrlSetHolder();
		urlHolder.setMainUrl(entryUrl);
		urlHolder.setCrossDomain(false);
		urlHolder.addUrl(entryUrl);
		urlHolder.markUrl(entryUrl);
		jsoupParser.setUrlHolder(urlHolder);
		
		//开始处理过程
		jsoupParser.parseHtml(html, entryUrl);
		loop(jsoupParser, urlHolder, entryUrl,outputDir);
		HttpClientHolder.close();
		System.out.println("total save file num :" + count);
	}

	public static void loop(JsoupParser jsoupParser, UrlSetHolder urlHolder, String entryUrl,
			String outputDir) throws Exception {
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
			loop(jsoupParser, urlHolder, entryUrl, outputDir);

	}

}
