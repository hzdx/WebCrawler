package com.mycom.webcrawler.infoextractor;

public interface InfoExtractor<T> {
	
	/**
	 * define how to get useful information from html page.
	 * @param url the web page absolute url
	 * @param htmlContent the web page content
	 * @return the Object we can parse from the page
	 */
	T extract(String url ,String htmlContent);
}
