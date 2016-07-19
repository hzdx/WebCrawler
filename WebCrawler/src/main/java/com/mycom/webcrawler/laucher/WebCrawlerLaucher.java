package com.mycom.webcrawler.laucher;

import com.mycom.webcrawler.httpclient.CustomHttpClientWrapper;
import com.mycom.webcrawler.model.UrlWrapper;
import com.mycom.webcrawler.parser.JsoupParser;
import com.mycom.webcrawler.urlholder.UrlHolder;
import com.mycom.webcrawler.util.FileUtil;

public class WebCrawlerLaucher {
	public static int count = 0;

	public static void main(String[] args) throws Exception {
		String urlPrefix = "http://www.liaoxuefeng.com/wiki/";
		String entryUrl = "http://www.liaoxuefeng.com/wiki/001434446689867b27157e896e74d51a89c25cc8b43bdb3000";
		String outputDir = "d:/crawler/js/";
		String html = CustomHttpClientWrapper.fetchUrl(entryUrl).getHtml();
		FileUtil.saveFile(html, entryUrl.replaceAll(urlPrefix, outputDir), false);
		count++;

		// 初始化jsoupParser,urlHolder组件
		JsoupParser jsoupParser = new JsoupParser();
		UrlHolder urlHolder = new UrlHolder();
		urlHolder.addUrl(entryUrl);
		urlHolder.markUrl(entryUrl);
		jsoupParser.setUrlHolder(urlHolder);
		jsoupParser.setPrefixUrl(urlPrefix);

		// 开始处理过程
		jsoupParser.parseHtml(html, entryUrl);
		loops(jsoupParser, urlHolder, urlPrefix, outputDir);
		CustomHttpClientWrapper.close();
		System.out.println("total save file num :" + count);
	}

	public static void loops(JsoupParser jsoupParser, UrlHolder urlHolder, String urlPrefix, String outputDir)
			throws Exception {
		for (String url : urlHolder.getUncrawlUrl()) {
			UrlWrapper wrapper = CustomHttpClientWrapper.fetchUrl(url);
			if (wrapper != null) {
				FileUtil.saveFile(wrapper.getHtml(), wrapper.getUrl().replaceAll(urlPrefix, outputDir),false);
				jsoupParser.parseHtml(wrapper.getHtml(), wrapper.getUrl());
			}
			count++;
			urlHolder.markUrl(url);
		}

		if (!urlHolder.isCompleted())
			loops(jsoupParser, urlHolder, urlPrefix, outputDir);

	}

}
