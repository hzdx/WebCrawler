package com.mycom.webcrawler.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.webcrawler.httpclient.HttpClientWrapper;
import com.mycom.webcrawler.parser.JsoupParser;

public class CrawlerRunner implements Runnable {
	private Logger log = LoggerFactory.getLogger(CrawlerRunner.class);
	private BlockingQueue<String> urlQueue;
	private JsoupParser parser;

	@Override
	public void run() {
		while (true) {
			try {
				String url = urlQueue.poll(10, TimeUnit.SECONDS);
				if (url != null) {
					String html = HttpClientWrapper.fetchUrl(url);
					parser.parseHtml(html, url);
				} else
					break;
			} catch (Exception e) {
				log.error(e.getMessage());
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

}
