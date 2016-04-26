package com.mycom.jsoup.test;

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	public static String doget(String url) throws Exception {
		
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(10*1000)
		        .setConnectionRequestTimeout(10*1000)
		        .setConnectTimeout(10*1000)
		        .build();
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {		
			@Override
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 5) {
		            // Do not retry if over max retry count
		            return false;
		        }
		        if (exception instanceof UnknownHostException) {
		            // Unknown host
		            return false;
		        }
				return true;
			}
		};
		CloseableHttpClient httpclient = HttpClients.custom()//自定义
		        .setDefaultRequestConfig(requestConfig)
				.setRetryHandler(retryHandler)
				.build();//
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			HttpClientContext context = HttpClientContext.create();
			System.out.println("executing request " + httpget.getURI());
			httpget.addHeader("User-Agent","Mozilla/5.0 Chrome/50.0.2661.75");
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget,context);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				 HttpHost target = context.getTargetHost();
				    List<URI> redirectLocations = context.getRedirectLocations();
				    URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
				    System.out.println("Final HTTP location: " + location.toASCIIString());
				    // Expected to be an absolute URI
				// 打印响应状态
				System.out.println("statusLine:" + response.getStatusLine());
				if (entity != null) {
					System.out
							.println("--------------------------------------");
					System.out.println("response header:"
							+ entity.getContentType().toString());
					// 打印响应内容
					String s = EntityUtils.toString(entity);
					//System.out.println(s);
					return s;
				}
				else return null;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(doget("https://maven.apache.org/download.html"));
	}
}
