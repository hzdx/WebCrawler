package com.mycom.jsoup.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	public static String doget(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			System.out.println("executing request " + httpget.getURI());
			httpget.addHeader("User-Agent","Mozilla/5.0 Chrome/50.0.2661.75");
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println("statusLine:" + response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: "
							+ entity.getContentLength());
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
		System.out.println(doget("https://maven.apache.org/"));
	}
}
