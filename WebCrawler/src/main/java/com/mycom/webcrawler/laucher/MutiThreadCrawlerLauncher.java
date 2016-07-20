package com.mycom.webcrawler.laucher;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.mycom.webcrawler.htmlhandler.DownloadHandler;
import com.mycom.webcrawler.httpclient.HttpClientWrapper;
import com.mycom.webcrawler.model.UrlConfig;
import com.mycom.webcrawler.parser.JsoupParser;
import com.mycom.webcrawler.thread.CrawlerRunner;
import com.mycom.webcrawler.urlholder.ConcurrentUrlHolder;
import com.mycom.webcrawler.util.FileUtil;

public class MutiThreadCrawlerLauncher {
	private String urlPrefix;
	private String entryUrl;
	private String outputDir;

	public void launch() throws Exception {
		String html = HttpClientWrapper.fetchUrl(entryUrl);
		FileUtil.saveToLocal(html, outputDir, "主页");

		BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>();
		// 初始化jsoupParser,urlHolder组件
		JsoupParser jsoupParser = new JsoupParser();
		ConcurrentUrlHolder urlHolder = new ConcurrentUrlHolder();// 存放要解析的url
		urlHolder.setUrlQueue(urlQueue);
		urlHolder.getUrlSet().add(entryUrl);
		jsoupParser.setUrlHolder(urlHolder);

		UrlConfig config = new UrlConfig(entryUrl, urlPrefix);
		jsoupParser.setConfig(config);

		jsoupParser.addPageHandler(new DownloadHandler(outputDir));
		// 处理链接

		// 第一次解析
		jsoupParser.parseHtml(html, entryUrl);

		int threadNum = Runtime.getRuntime().availableProcessors() * 2;
		//System.out.println("thread num:" + threadNum);
		ExecutorService service = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) {
			CrawlerRunner runner = new CrawlerRunner();
			runner.setParser(jsoupParser);
			runner.setUrlQueue(urlQueue);
			service.execute(runner);
		}

		service.shutdown();//主线程不等待
		service.awaitTermination(Integer.MAX_VALUE,TimeUnit.SECONDS);
		//以上两个方法一起阻塞等待线程完成
		HttpClientWrapper.close();

	}

	public static void main(String[] args) throws Exception {
		String urlPrefix = "http://www.liaoxuefeng.com/wiki/";
		String entryUrl = "http://www.liaoxuefeng.com/wiki/001434446689867b27157e896e74d51a89c25cc8b43bdb3000";
		MutiThreadCrawlerLauncher launcher = new MutiThreadCrawlerLauncher();
		String outputDir = "d:/crawler/js2/";
		launcher.setEntryUrl(entryUrl);
		launcher.setUrlPrefix(urlPrefix);
		launcher.setOutputDir(outputDir);
		launcher.launch();
		System.out.println("done!");
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	public String getEntryUrl() {
		return entryUrl;
	}

	public void setEntryUrl(String entryUrl) {
		this.entryUrl = entryUrl;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

}
