package com.mycom.webcrawler.laucher;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.mycom.webcrawler.compnent.CrawlerRunner;
import com.mycom.webcrawler.compnent.JsoupParser;
import com.mycom.webcrawler.htmlhandler.LinkHandler;
import com.mycom.webcrawler.httpclient.HttpClientHolder;
import com.mycom.webcrawler.httpclient.SimpleHttpClientHolder;
import com.mycom.webcrawler.urlholder.AbstractUrlHolder;
import com.mycom.webcrawler.urlholder.ConcurrentUrlHolder;
import com.mycom.webcrawler.urlholder.UrlHolder;

public class MutiThreadCrawlerLaucher {

	public static void main(String[] args) throws Exception {
		String entryUrl = "http://shanghai.anjuke.com/sale/";
		String usefulUrl = entryUrl + "p";
		String targetUrlPrefix = "http://shanghai.anjuke.com/prop/view/";
		String html = SimpleHttpClientHolder.fetchUrl(entryUrl);

		BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>();
		// 初始化JsoupUtil,urlHolder组件
		JsoupParser jsoupParser = new JsoupParser();
		ConcurrentUrlHolder urlHolder = new ConcurrentUrlHolder();// 存放要解析的url
		urlHolder.setUrlQueue(urlQueue);
		jsoupParser.setUrlHolder(urlHolder);
		jsoupParser.setPrefixUrl(entryUrl);
		// jsoupParser.setFilter(new UriFilter());
		// jsoupParser.setHtmlHandler(new LinkHandler(targetUrlPrefix));
		// 处理链接

		// 第一次解析
		jsoupParser.parseHtmlOnlyLink(html, entryUrl);

		int thNum = 8;
		ExecutorService service = Executors.newFixedThreadPool(thNum);
		for (int i = 0; i < thNum; i++) {
			CrawlerRunner runner = new CrawlerRunner();
			runner.setParser(jsoupParser);
			runner.setUrlQueue(urlQueue);
			service.submit(runner);
		}

		service.shutdown();
		HttpClientHolder.close();
	}

}
