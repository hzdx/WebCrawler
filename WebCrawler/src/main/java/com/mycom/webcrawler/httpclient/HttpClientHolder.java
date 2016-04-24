package com.mycom.webcrawler.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientHolder {
	private static CloseableHttpClient httpClient = HttpClients.createDefault();
	
	public static HttpClient getInstance(){
		return httpClient;
	}
	
	public static void close() throws IOException{
		if(httpClient != null) httpClient.close();
	}

	//todo 文件类型，视频，音频类型的获取
	
	public static String fetchUrl(String url) throws Exception{
		String url0 = url.replace("[","").replace("\"", "").replace("\\", "/");
		if(!url0.startsWith("http")) return null; //过滤掉 url = FormElement.html的情况
		HttpGet httpget = new HttpGet(url0);
		httpget.addHeader("User-Agent","Mozilla/5.0 Chrome/50.0.2661.75");
		System.out.println("fetching :" + url);
		CloseableHttpResponse response =  httpClient.execute(httpget);
		try {
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			System.out.println("statusLine:" + response.getStatusLine());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && entity != null) {
				String content = EntityUtils.toString(entity);
				return content;
			}
			else return null;
		} finally {
			response.close();
		}
	}
}
