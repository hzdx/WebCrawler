package com.mycom.webcrawler.laucher;

import com.mycom.webcrawler.htmlhandler.DownloadHandler;
import com.mycom.webcrawler.httpclient.HttpClientUtil;
import com.mycom.webcrawler.model.CollectConfig;
import com.mycom.webcrawler.parser.JsoupParser;
import com.mycom.webcrawler.urlholder.UrlHolder;
import com.mycom.webcrawler.util.FileUtil;

public class WebCrawlerLauncher {
	private String urlPrefix;
	private String entryUrl;
	private String outputDir;

	public void launch() throws Exception {
		String html = HttpClientUtil.fetchUrl(entryUrl);
		FileUtil.saveToLocal(html, outputDir, "主页");

		// 初始化jsoupParser,urlHolder组件
		JsoupParser jsoupParser = new JsoupParser();
		UrlHolder urlHolder = new UrlHolder();
		urlHolder.addUrl(entryUrl);
		urlHolder.markUrl(entryUrl);
		jsoupParser.setUrlHolder(urlHolder);

		CollectConfig config = new CollectConfig(entryUrl, urlPrefix);
		jsoupParser.setConfig(config);
		jsoupParser.addPageHandler(new DownloadHandler(outputDir));

		// 开始处理过程
		jsoupParser.parseHtml(html, entryUrl);
		loops(jsoupParser, urlHolder);
		HttpClientUtil.close();
	}

	private void loops(JsoupParser jsoupParser, UrlHolder urlHolder) throws Exception {
		for (String url : urlHolder.getUncrawlUrl()) {
			String html = HttpClientUtil.fetchUrl(url);
			if (html != null) {
				jsoupParser.parseHtml(html, url);
			}

			urlHolder.markUrl(url);
		}

		if (!urlHolder.isCompleted())
			loops(jsoupParser, urlHolder);

	}

	public static void main(String[] args) throws Exception {
		WebCrawlerLauncher launcher = new WebCrawlerLauncher();
		String urlPrefix = "http://www.liaoxuefeng.com/wiki/";
		String entryUrl = "http://www.liaoxuefeng.com/wiki/001434446689867b27157e896e74d51a89c25cc8b43bdb3000";
		String outputDir = "d:/crawler/js/";
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
