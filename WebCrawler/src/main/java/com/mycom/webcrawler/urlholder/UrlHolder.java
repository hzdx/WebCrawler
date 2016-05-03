package com.mycom.webcrawler.urlholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlHolder extends AbstractUrlHolder {
	private Logger log = LoggerFactory.getLogger(UrlHolder.class);
	private Map<String, Boolean> urlMap = new HashMap<>();

	public void addUrl(String url) {
		if (urlMap.keySet().contains(url))
			return;
		log.info("urlHolder put :{}", url);
		urlMap.put(url, true);// 还未必解析为ture
	}

	@Deprecated
	public String filterUrl0(String url) {
		// url = filterUrl(url,"?");
		url = filterUrl(url, "#");
		return url;
	}

	/**
	 * 去掉url中特殊字符后面的部分 如 # ？
	 * 
	 * @param url
	 * @param ch
	 * @return
	 */
	@Deprecated
	private String filterUrl(String url, String ch) {
		if (url.contains(ch)) {
			int index = url.indexOf(ch);
			url = url.substring(0, index);
		}
		return url;
	}

	public void markUrl(String url) {
		if (urlMap.keySet().contains(url))
			urlMap.put(url, false);// 已经被解析为false
	}

	public List<String> getUncrawlUrl() {
		List<String> list = new ArrayList<>();
		for (Map.Entry<String, Boolean> entry : urlMap.entrySet()) {
			if (entry.getValue())
				list.add(entry.getKey());
		}
		return list;
	}

	public boolean isCompleted() {
		for (Map.Entry<String, Boolean> entry : urlMap.entrySet()) {
			if (entry.getValue())
				return false;
		}
		return true;
	}

}
