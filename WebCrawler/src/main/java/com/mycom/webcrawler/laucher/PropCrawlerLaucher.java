package com.mycom.webcrawler.laucher;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.mycom.webcrawler.compnent.JsoupParser;
import com.mycom.webcrawler.compnent.UrlSetHolder;
import com.mycom.webcrawler.httpclient.SimpleHttpClientHolder;
import com.mycom.webcrawler.util.FileUtil;

public class PropCrawlerLaucher {
	public static int count = 0;
	private static BufferedWriter bw = null;

	public static void main(String[] args) throws Exception {
		String entryUrl = "http://shanghai.anjuke.com/sale/";
		String outputDir = "d:/crawler/jsoup/";
		String html = SimpleHttpClientHolder.fetchUrl(entryUrl);
		//FileUtil.saveToLocal(html, outputDir, "index.html");
		count++;
		
		bw = new BufferedWriter(new FileWriter("prop.txt"));
		//初始化JsoupUtil,urlHolder组件
		JsoupParser jsoupParser = new JsoupParser();
		UrlSetHolder urlHolder = new UrlSetHolder();
		urlHolder.setMainUrl("http://shanghai.anjuke.com/prop/view/");
		urlHolder.setCrossDomain(false);
		urlHolder.addUrl(entryUrl);
		urlHolder.markUrl(entryUrl);
		jsoupParser.setUrlHolder(urlHolder);
		jsoupParser.setPrefix("http://shanghai.anjuke.com/prop/view/");
		
		//开始处理过程
		jsoupParser.parseHtml(html, entryUrl);
		loop(jsoupParser, urlHolder, entryUrl,outputDir);
		SimpleHttpClientHolder.close();
		bw.flush();
		bw.close();
		System.out.println("total save file num :" + count);
	}

	public static void loop(JsoupParser jsoupParser, UrlSetHolder urlHolder, String entryUrl,
			String outputDir) throws Exception {
		for (String url : urlHolder.getUncrawlUrl()) {
			String html = SimpleHttpClientHolder.fetchUrl(url);
			if (html != null) {
				//FileUtil.saveFile(html, url.replaceAll(entryUrl, outputDir));
				jsoupParser.parseHtml(html, url);
				bw.write(url);
				bw.newLine();
			}
			count++;
			urlHolder.markUrl(url);
		}

		if (!urlHolder.isCompleted())
			loop(jsoupParser, urlHolder, entryUrl, outputDir);

	}

}
