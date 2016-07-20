package com.mycom.webcrawler.model;

import java.net.MalformedURLException;

import com.mycom.webcrawler.util.StringUtil;

public class UrlConfig {
	public enum UrlType {
		LINK, // a[href]
		MEDIA, // src
		IMPORT;// link[href]
	}

	private String entryUrl;// 入口url地址
	private String prefix;// 抓取url的前缀要求
	private UrlType[] types;// 抓取url的类型
	public static UrlType[] defaultType = { UrlType.LINK };

	public static UrlConfig create(String entryUrl) throws MalformedURLException {
		return new UrlConfig(entryUrl);
	}

	public UrlConfig(String entryUrl, String prefix, UrlType[] types) {
		this.entryUrl = entryUrl;
		this.prefix = prefix;
		this.types = types;
	}

	public UrlConfig(String entryUrl, String prefix) {
		this(entryUrl, prefix, defaultType);
	}

	public UrlConfig(String entryUrl) throws MalformedURLException {
		this(entryUrl, StringUtil.getMainUrl(entryUrl), defaultType);
	}

	public UrlType[] getTypes() {
		return types;
	}

	public void setType(UrlType[] types) {
		this.types = types;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getEntryUrl() {
		return entryUrl;
	}

	public void setEntryUrl(String entryUrl) {
		this.entryUrl = entryUrl;
	}

}
