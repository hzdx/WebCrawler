package com.mycom.webcrawler.htmlhandler;

import org.jsoup.nodes.Document;

public interface PageHandler {

	/**
	 * How to deal with html page
	 * 
	 * @param htmlContent
	 *            page html content
	 * @param url
	 *            page's absolute url
	 * @param pageDoc
	 *            document object after jsoup parse
	 * @return
	 * @throws Exception
	 */
	void process(String htmlContent, String url, Document pageDoc) throws Exception;

}
