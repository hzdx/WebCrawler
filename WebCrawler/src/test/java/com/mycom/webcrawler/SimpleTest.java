package com.mycom.webcrawler;

import java.net.URI;

import org.junit.Test;

import com.mycom.webcrawler.httpclient.HttpClientUtil;

public class SimpleTest {
	@Test
	public void testUrl() throws Exception {
		String baseUrl = "https://maven.apache.org/aaa/bb/";
		String pageUrl = "/";
		URI uri = URI.create(baseUrl);
		URI uri1 = URI.create(pageUrl);
		System.out.println(uri.resolve(pageUrl));// 父路径在前
	}
	
	@Test
	public void testSplit(){
		String str = "https://jsoup.org/aa/../serialized-form.html";
		//System.out.println(str.contains("../"));
		//String[] arr = str.split("(\\.\\./)+");// 以 ../划分
		// .好像是特殊字符 用双反斜杠进行转义  .不转义，匹配任意一个字符
		String[] arr = str.split("../");
		for(String s:arr) System.out.println(s);
	}
	
	@Test
	public void testUri(){
		String baseUrl = "https://maven.apache.org/aa";
		URI uri = URI.create(baseUrl);
		URI uri2 = uri.resolve("");
		System.out.println(uri2);
		System.out.println(uri.getPath());
		System.out.println(uri.getRawPath());
		if(!uri.getPath().startsWith("/")){
			//uri = new URI(uri);
		}
	}
	
	/**
	 * test for {@link HttpClientUtil#fetchUrl(String)}}
	 * @throws Exception
	 */
	@Test
	public void testHttpUtil() throws Exception{
		String url = "https://www.baidu.com";
		System.out.println(HttpClientUtil.fetchUrl(url));
	}

}
