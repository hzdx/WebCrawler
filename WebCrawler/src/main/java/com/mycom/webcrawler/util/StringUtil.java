package com.mycom.webcrawler.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private static Logger log = LoggerFactory.getLogger(StringUtil.class);
	
	public static boolean isBlank(String str) {
		return str == null || str.trim().equals("");
	}
	
	public static String getMainUrl(String url) throws MalformedURLException{
		URL url0 = new URL(url);
		return url.replace(url0.getPath(), "/");		
	}

	/**
	 * 整理url,去掉空格和特殊符号
	 * 
	 * @param url
	 * @return
	 */
	public static String cleanUrl(String uri) {
		if (uri.equals("") || uri.equals("#") || uri.equals("/"))
			return null;// 过滤掉 #,/等链接
		uri = uri.replace("[", "").replace("\"", "").replace("\\", "/");
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

	/**
	 * 从页面上的url解析出绝对的url
	 * 
	 * @param url
	 *            页面上的url,可能是相对路径
	 * @param baseUrl
	 *            当前页面的url
	 * @return
	 */
	public static String resolveUrl(String url, String baseUrl) {
		URI baseUri = URI.create(baseUrl);
		URI finalUri = baseUri.resolve(url);
		return finalUri.toASCIIString();
	}

	/**
	 * 获取绝对url
	 * 
	 * @param baseUrl
	 *            当前url
	 * @param relativeUrl
	 *            解析到的url
	 * @return
	 */
	@Deprecated
	public static String getAbsUrl(String baseUrl, String pageUrl) {
		// pageUrl = pageUrl.trim();
		if (pageUrl.startsWith(baseUrl) || pageUrl.startsWith("http://") || pageUrl.startsWith("https://")) {
			log.info("abs url :{}", pageUrl);
			return transUrl(baseUrl, pageUrl);
		}
		if (baseUrl.lastIndexOf("/") != baseUrl.length() - 1) {
			if (baseUrl.endsWith(".html") || baseUrl.endsWith(".js") || baseUrl.endsWith(".css")) {
				// 以.html,.js,.css结尾的静态资源,返回上一级
				baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
			}
			// 如果最后一个字符不是/ ,加上/
			baseUrl = baseUrl + "/";
		}
		if (pageUrl.startsWith("./")) {// 以./开头的url
			if (pageUrl.length() > 2)
				pageUrl = pageUrl.substring(2, pageUrl.length());
			else
				pageUrl = "";
		}
		if (pageUrl.startsWith("/")) { // 以/开头的url
			if (pageUrl.length() > 1)
				pageUrl = pageUrl.substring(1, pageUrl.length());
			else
				pageUrl = "";
		}
		String combineUrl = baseUrl + pageUrl;
		log.info("combine url :{}", combineUrl);
		return transUrl(baseUrl, combineUrl);
	}

	/**
	 * 对类似 ../../serialized-form.html 类型的链接进行转换<br>
	 * 
	 * @param baseUrl
	 *            入口url
	 * @param resultUrl
	 *            jsoup得到的结果url
	 * @return 实际的url
	 */
	@Deprecated
	public static String transUrl(String baseUrl, String resultUrl) {
		if (!resultUrl.contains("../"))
			return resultUrl;
		log.info(" url before trans :" + resultUrl);
		log.info(" baseUrl :" + baseUrl);
		String[] arr = resultUrl.split("/", -1);
		int n = 0;
		for (String str : arr) {
			if ("..".equals(str))
				n++;
		}
		// System.out.println(n);
		// 1.得到resultUrl中 ../ 的个数n
		if (baseUrl.lastIndexOf("/") == baseUrl.length() - 1) // 如果最后一个字符是/ ,去掉/
			baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
		arr = baseUrl.split("/", -1);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length - n - 1; i++) {
			sb.append(arr[i] + "/");
		}
		// System.out.println(sb.toString());
		// 2.baseUrl 回退n+1个位置<br>
		String[] resultUrlArr = resultUrl.split("(\\.\\./)+");
		String expectUrl = sb.toString() + resultUrlArr[resultUrlArr.length - 1];
		log.info(" url after trans :" + expectUrl);
		// 3.第二步的结果加上 resultUrl ../后面的内容
		return expectUrl;
	}

	

}
