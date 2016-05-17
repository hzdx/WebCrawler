package com.mycom.webcrawler.laucher;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.mycom.webcrawler.component.CrawlerRunner;
import com.mycom.webcrawler.component.JsoupParser;
import com.mycom.webcrawler.htmlhandler.LinkHandler;
import com.mycom.webcrawler.httpclient.HttpClientHolder;
import com.mycom.webcrawler.httpclient.SimpleHttpClientHolder;
import com.mycom.webcrawler.urlholder.ConcurrentUrlHolder;

public class MutiThreadCrawlerLaucher {

	public static void main(String[] args) throws Exception {
		String entryUrl = "http://shanghai.anjuke.com/sale/";
		String targetUrlPrefix = "http://shanghai.anjuke.com/prop/view/";
		String html = SimpleHttpClientHolder.fetchUrl(entryUrl);

		BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>();
		// 初始化jsoupParser,urlHolder组件
		JsoupParser jsoupParser = new JsoupParser();
		ConcurrentUrlHolder urlHolder = new ConcurrentUrlHolder();// 存放要解析的url
		urlHolder.setUrlQueue(urlQueue);
		urlHolder.getUrlSet().add(entryUrl);
		jsoupParser.setUrlHolder(urlHolder);
		jsoupParser.setPrefixUrl(entryUrl);
		jsoupParser.setPageHandler(new LinkHandler(targetUrlPrefix));
		// 处理链接

		// 第一次解析
		jsoupParser.parseHtmlOnlyLink(html, entryUrl);

		int threadNum = 8;
		ExecutorService service = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) {
			CrawlerRunner runner = new CrawlerRunner();
			runner.setParser(jsoupParser);
			runner.setUrlQueue(urlQueue);
			service.submit(runner);
		}

		service.shutdown();
		HttpClientHolder.close();
	}

}
