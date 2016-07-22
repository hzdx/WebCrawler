package com.mycom.webcrawler.httpclient;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

public class WebCrawlerRetryHandler implements HttpRequestRetryHandler {
	private final int retryTimes = Integer.parseInt(System.getProperty("http.retry", "5"));

	@Override
	public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
		if (executionCount >= retryTimes) {
			// Do not retry if over max retry count
			return false;
		}
		if (exception instanceof UnknownHostException) {
			// Unknown host
			return false;
		}
		return true;
	}
}
