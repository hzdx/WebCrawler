package com.mycom.webcrawler.compnent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.mycom.webcrawler.httpclient.SimpleHttpClientHolder;

public class CrawlerRunner implements Runnable {
	private BlockingQueue<String> urlQueue;
	private JsoupParser parser;
	private String baseUrl;

	@Override
	public void run() {
		while (true) {
			try {
				String url = urlQueue.poll(10, TimeUnit.SECONDS);
				String html = SimpleHttpClientHolder.fetchUrl(url);
				parser.parseHtmlOnlyLink(html, baseUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public BlockingQueue<String> getUrlQueue() {
		return urlQueue;
	}

	public void setUrlQueue(BlockingQueue<String> urlQueue) {
		this.urlQueue = urlQueue;
	}

	public JsoupParser getParser() {
		return parser;
	}

	public void setParser(JsoupParser parser) {
		this.parser = parser;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	

}
