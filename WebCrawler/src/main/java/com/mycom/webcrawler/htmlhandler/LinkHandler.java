package com.mycom.webcrawler.htmlhandler;

import org.jsoup.nodes.Document;

import com.mycom.webcrawler.persistence.CommonDao;

/**
 * 处理链接的handler
 * 
 * @author l
 *
 */
public class LinkHandler implements PageHandler {
	private String prefixUrl;

	public LinkHandler(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}

	public String getPrefixUrl() {
		return prefixUrl;
	}

	public void setPrefixUrl(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}

	public void saveWantedUrl(String url) throws Exception {
		if (url.startsWith(prefixUrl)) {
			String sql = "insert into prop_url(url) values (?)";
			CommonDao.insert(sql, url);
		}

	}

	@Override
	public void process(String htmlContent, String url, Document pageDoc) throws Exception {
		// TODO Auto-generated method stub
		saveWantedUrl(url);
	}



}
