package com.mycom.webcrawler.htmlhandler;

import org.jsoup.nodes.Document;

import com.mycom.webcrawler.persistence.CommonDao;

/**
 * 处理链接的handler
 * @author l
 *
 */
public class LinkHandler implements HtmlHandler{
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
	
	public void saveWantedUrl(String url) throws Exception{
		if(url.startsWith(prefixUrl)){
			String sql = "insert into prop_url(url) values (?)";
			CommonDao.insert(sql, url);
		}
			
	}
	@Override
	public Object process(String html, Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

}
