package com.mycom.webcrawler;

import com.mycom.webcrawler.httpclient.HttpClientHolder;

public class WebCrawlerLaucher {
	public static int count = 0;

	public static void main(String[] args) throws Exception {
		String entryUrl = "https://maven.apache.org/";
		String outputDir = "d:/crawler/maven/";
		String html = HttpClientHolder.fetchUrl(entryUrl);
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
		jsoupParser.parseUrl(html, entryUrl);
		loop(jsoupParser, urlHolder, entryUrl,outputDir);
		HttpClientHolder.close();
		System.out.println("total save file num :" + count);
	}

	public static void loop(JsoupParser jsoupParser, UrlSetHolder urlHolder, String entryUrl,
			String outputDir) throws Exception {
		for (String url : urlHolder.getUncrawlUrl()) {
			String subhtml = HttpClientHolder.fetchUrl(url);
			if (subhtml != null) {
				FileUtil.saveFile(subhtml, url.replaceAll(entryUrl, outputDir));
				jsoupParser.parseUrl(subhtml, url);
			}
			count++;
			urlHolder.markUrl(url);
		}

		if (!urlHolder.isCompleted())
			loop(jsoupParser, urlHolder, entryUrl, outputDir);

	}

}
