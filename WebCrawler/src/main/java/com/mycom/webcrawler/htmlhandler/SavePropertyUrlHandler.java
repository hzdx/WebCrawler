package com.mycom.webcrawler.htmlhandler;

import org.jsoup.nodes.Document;

import com.mycom.webcrawler.persistence.PropertyService;

/**
 * 保存从页面解析到的房屋信息url的handler
 * 
 */
public class SavePropertyUrlHandler implements PageHandler {
	private String prefixUrl;

	public SavePropertyUrlHandler(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}

	public String getPrefixUrl() {
		return prefixUrl;
	}

	public void setPrefixUrl(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}

	@Override
	public void process(String htmlContent, String url, Document pageDoc) throws Exception {
		if (url.startsWith(prefixUrl)) {
			PropertyService.saveUrl(url);
		}
	}

}
