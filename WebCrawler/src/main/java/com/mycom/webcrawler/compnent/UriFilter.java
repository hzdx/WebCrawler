package com.mycom.webcrawler.compnent;

@Deprecated
public class UriFilter {
	
	public String filter(String uri){
		uri = uri.trim().replace("[","")
				.replace("\"", "").replace("\\", "/");
		if(uri.contains("?")){
			int index = uri.indexOf("?");
			uri = uri.substring(0, index);
		}
		if(uri.contains("#")){
			int index = uri.indexOf("#");
			uri = uri.substring(0, index);
		}
		if(uri.startsWith("javascript")) return null;
		return uri;
	}

	
}
