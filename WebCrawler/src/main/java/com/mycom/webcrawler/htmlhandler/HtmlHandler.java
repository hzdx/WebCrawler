package com.mycom.webcrawler.htmlhandler;

import org.jsoup.nodes.Document;

public interface HtmlHandler {
	/**
	 * 处理html文本
	 * 
	 * @param html
	 *            页面内容
	 * @param doc
	 *            jsoup解析的文件对象
	 * @return
	 */
	Object process(String html, Document doc);

}
