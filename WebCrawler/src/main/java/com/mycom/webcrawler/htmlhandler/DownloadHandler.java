package com.mycom.webcrawler.htmlhandler;

import org.jsoup.nodes.Document;

import com.mycom.webcrawler.util.FileUtil;

public class DownloadHandler implements PageHandler {
	private String dir;// output directory
	private String extension;// filename extension

	public DownloadHandler(String dir, String extension) {
		this.dir = dir;
		this.extension = extension;
	}

	@Override
	public void process(String htmlContent, String url, Document pageDoc) throws Exception {
		String title = pageDoc.getElementsByTag("title").first().text();
		FileUtil.saveToLocal(htmlContent, dir, title + "." + extension);
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
