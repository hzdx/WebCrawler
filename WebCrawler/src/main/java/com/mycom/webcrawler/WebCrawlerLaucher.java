package com.mycom.webcrawler;

import com.mycom.webcrawler.httpclient.HttpClientHolder;

public class WebCrawlerLaucher {
	public static int count = 0;

	public static void main(String[] args) throws Exception {
		String entryUrl = "https://maven.apache.org/";
		String outputDir = "d:/crawler/maven";
		String html = HttpClientHolder.fetchUrl(entryUrl);
		FileUtil.saveToLocal(html, outputDir, "index.html");
		count++;
		
		//初始化JsoupUtil,urlHolder组件
		JsoupUtil jsoupUtil = new JsoupUtil();
		UrlSetHolder urlHolder = new UrlSetHolder();
		urlHolder.addUrl(entryUrl);
		urlHolder.markUrl(entryUrl);
		jsoupUtil.setUrlHolder(urlHolder);
		
		//开始处理过程
		jsoupUtil.parseUrl(html, entryUrl);
		loop(jsoupUtil, urlHolder, entryUrl, entryUrl, outputDir);
		HttpClientHolder.close();
		System.out.println("total save file num :" + count);
	}

	public static void loop(JsoupUtil jsoupUtil, UrlSetHolder urlHolder, String entryUrl, String baseUrl,
			String outputDir) throws Exception {
		for (String url : urlHolder.getUncrawlUrl()) {
			// if(!url.startsWith(entryUrl)) continue;
			String subhtml = HttpClientHolder.fetchUrl(url);
			if (subhtml != null) {
				FileUtil.saveFile(subhtml, url.replaceAll(baseUrl, outputDir));
				jsoupUtil.parseUrl(subhtml, entryUrl);
			}
			count++;
			urlHolder.markUrl(url);
		}

		if (!urlHolder.isCompleted())
			loop(jsoupUtil, urlHolder, entryUrl, baseUrl, outputDir);

	}

}
