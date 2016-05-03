package com.mycom.webcrawler.laucher;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.mycom.webcrawler.compnent.JsoupParser;
import com.mycom.webcrawler.httpclient.SimpleHttpClientHolder;
import com.mycom.webcrawler.urlholder.UrlHolder;

public class PropCrawlerLaucher {
	public static int count = 0;
	private static BufferedWriter bw = null;

	public static void main(String[] args) throws Exception {
		String entryUrl = "http://shanghai.anjuke.com/sale/";
		String outputDir = "d:/crawler/jsoup/";
		String html = SimpleHttpClientHolder.fetchUrl(entryUrl);
		count++;

		bw = new BufferedWriter(new FileWriter("prop.txt"));
		// 初始化jsoupParser,urlHolder组件
		JsoupParser jsoupParser = new JsoupParser();
		UrlHolder urlHolder = new UrlHolder();
		jsoupParser.setUrlHolder(urlHolder);
		jsoupParser.setPrefixUrl("http://shanghai.anjuke.com/prop/view/");

		// 开始处理过程
		jsoupParser.parseHtml(html, entryUrl);
		loops(jsoupParser, urlHolder, entryUrl);
		SimpleHttpClientHolder.close();
		bw.flush();
		bw.close();
		System.out.println("total save file num :" + count);
	}

	public static void loops(JsoupParser jsoupParser, UrlHolder urlHolder, String entryUrl)
			throws Exception {
		for (String url : urlHolder.getUncrawlUrl()) {
			String html = SimpleHttpClientHolder.fetchUrl(url);
			if (html != null) {
				jsoupParser.parseHtml(html, url);
				bw.write(url);
				bw.newLine();
			}
			count++;
			urlHolder.markUrl(url);
		}

		if (!urlHolder.isCompleted())
			loops(jsoupParser, urlHolder, entryUrl);

	}

}
