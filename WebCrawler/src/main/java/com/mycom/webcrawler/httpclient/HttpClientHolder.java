package com.mycom.webcrawler.httpclient;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.webcrawler.model.UrlWrapper;

public class HttpClientHolder {
	private static Logger log = LoggerFactory.getLogger(HttpClientHolder.class);

	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10 * 1000)
			.setConnectionRequestTimeout(10 * 1000).setConnectTimeout(10 * 1000).build();
	private static HttpRequestRetryHandler retryHandler = new WebCrawlerRetryHandler();
	private static CloseableHttpClient httpClient = HttpClients.custom()// 自定义
			.setDefaultRequestConfig(requestConfig).setRetryHandler(retryHandler)
			.setUserAgent("Mozilla/5.0 Chrome/50.0.2661.75").build();//
	private static HttpClientContext context = HttpClientContext.create();

	// todo 文件类型，视频，音频类型的获取
	public static UrlWrapper fetchUrl(String url0) throws Exception {
		HttpGet httpget = new HttpGet(url0);
		log.info("httpClient fetching :" + url0);
		CloseableHttpResponse response = httpClient.execute(httpget, context);
		try {
			HttpEntity entity = response.getEntity();
			String status = response.getStatusLine().toString();
			log.info("status :{}", status);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && entity != null) {
				// todo read timeout 估计因为是加了 HttpClientContext
				UrlWrapper wrapper = new UrlWrapper();
				// 获取重定向之后的url
				HttpHost target = context.getTargetHost();
				List<URI> redirectLocations = context.getRedirectLocations();
				URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
				String finalUrl = location.toASCIIString();
				if (!(url0).equals(finalUrl)) {
					log.info("final http location: {}", finalUrl);
					wrapper.setUrl(finalUrl);
				} else {
					wrapper.setUrl(url0);
				}
				String content = EntityUtils.toString(entity);
				wrapper.setHtml(content);
				return wrapper;
			} else {
				log.error("url: {} ,status: {} ", url0, status);
				return null;
			}
		} finally {
			response.close();
		}
	}

	public static HttpClient getInstance() {
		return httpClient;
	}

	public static void close() throws IOException {
		if (httpClient != null)
			httpClient.close();
	}
}
