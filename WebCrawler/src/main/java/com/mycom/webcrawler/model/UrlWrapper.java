package com.mycom.webcrawler.model;

/**
 * url和html的包装对象
 */
public class UrlWrapper {
	private String url;
	private String html;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public UrlWrapper(String url, String html) {
		this.url = url;
		this.html = html;
	}
	public UrlWrapper(){}
}
