package com.mycom.webcrawler.htmlhandler;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.webcrawler.util.FileUtil;

public class DownloadHandler implements PageHandler {
	private final Logger log = LoggerFactory.getLogger(DownloadHandler.class);
	public static final String HTML_SUFFIX = ".html";
	private String dir;// output directory
	private String extension;// filename extension

	public DownloadHandler(String dir) {
		this.dir = dir;
		this.extension = HTML_SUFFIX;
	}

	public DownloadHandler(String dir, String extension) {
		this.dir = dir;
		this.extension = extension;
	}

	@Override
	public void process(String htmlContent, String url, Document pageDoc) {
		try {
			String title = pageDoc.getElementsByTag("title").first().text();
			FileUtil.saveToLocal(htmlContent, dir, title);
			// FileUtil.saveFullName(htmlContent, dir, title , extension);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
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
