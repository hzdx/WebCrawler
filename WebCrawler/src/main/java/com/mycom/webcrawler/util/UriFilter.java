package com.mycom.webcrawler.util;

@Deprecated
public class UriFilter {

	public String filter(String uri) {
		uri = uri.trim().replace("[", "").replace("\"", "").replace("\\", "/");
		if (uri.contains("?")) {
			int index = uri.indexOf("?");
			uri = uri.substring(0, index);
		}
		if (uri.contains("#")) {
			int index = uri.indexOf("#");
			uri = uri.substring(0, index);
		}
		if (uri.startsWith("javascript") || uri.contains("mailto"))
			return null;
		return uri;
	}

}
