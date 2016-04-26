package com.mycom.webcrawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private static Logger log = LoggerFactory.getLogger(StringUtil.class);

	public static void main(String[] args) {
//		String baseUrl = "https://jsoup.org/apidocs/org/jsoup/Connection.html";
//		String pageUrl = "https://jsoup.org/../../../aaa/serialized-form.html";
//		String expectUrl = "https://jsoup.org/apidocs/serialized-form.html";
//		System.out.println(transUrl(baseUrl, resultUrl));
//		String baseUrl = "https://maven.apache.org/";
//		String pageUrl = "./css/site.css";
//		System.out.println(getAbsUrl(baseUrl,pageUrl));
		System.out.println(("  abc ").trim());
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
	//todo url重合问题 如 https://maven.apache.org/plugins/plugins/maven-shade-plugin/
	public static String getAbsUrl(String baseUrl, String pageUrl) {
		//pageUrl = pageUrl.trim();
		if (pageUrl.startsWith(baseUrl) || pageUrl.startsWith("http://") || pageUrl.startsWith("https://")){
			log.info("abs url :{}",pageUrl);
			return transUrl(baseUrl,pageUrl);
		}	
		if (baseUrl.lastIndexOf("/") != baseUrl.length() - 1) {
			if(baseUrl.endsWith(".html") || baseUrl.endsWith(".js")
					|| baseUrl.endsWith(".css")){
				// 以.html,.js,.css结尾的静态资源,返回上一级
				baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
			}
			// 如果最后一个字符不是/ ,加上/
			baseUrl = baseUrl + "/";
		}
		if (pageUrl.startsWith("./")) {// 以./开头的url
			if(pageUrl.length()>2)
				pageUrl = pageUrl.substring(2,pageUrl.length());
			else pageUrl = "";
		}
		if(pageUrl.startsWith("/")){	//以/开头的url
			if(pageUrl.length()>1)
				pageUrl = pageUrl.substring(1,pageUrl.length());
			else pageUrl = "";
		}
		String combineUrl = baseUrl + pageUrl;
		log.info("combine url :{}",combineUrl);
		return transUrl(baseUrl,combineUrl);
	}

	/**
	 * 对类似 /../../../serialized-form.html 类型的链接进行转换<br>
	 * 
	 * @param baseUrl
	 *            入口url
	 * @param resultUrl
	 *            jsoup得到的结果url
	 * @return 实际的url
	 */
	// todo 出现了 https://configure.html 的url，页面的url无规则
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
		for (int i = 0; i < arr.length - n -1; i++) {
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

	public static boolean isBlank(String str) {
		if (str == null || str.trim().equals(""))
			return true;
		return false;
	}

}
