package com.mycom.webcrawler;

public class StringUtil {

	public static void main(String[] args) {
		String baseUrl = "https://jsoup.org/apidocs/org/jsoup/Connection.html";
		String resultUrl = "https://jsoup.org/../../../serialized-form.html";
		String expectUrl = "https://jsoup.org/apidocs/serialized-form.html";
		System.out.println(transUrl(baseUrl, resultUrl));
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
	//todo 出现了 https://configure.html 的url，检查是否有问题
	public static String transUrl(String baseUrl, String resultUrl) {
		if (!resultUrl.contains("../"))
			return resultUrl;
		String[] arr = resultUrl.split("/", -1);
		int n = 0;
		for (String str : arr) {
			if ("..".equals(str))
				n++;
		}
		// System.out.println(n);
		// 1.得到resultUrl中 ../ 的个数n<br>
		if (baseUrl.lastIndexOf("/") == baseUrl.length() - 1) // 如果最后一个字符是/ ,去掉/
			baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
		arr = baseUrl.split("/", -1);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length - n; i++) {
			sb.append(arr[i] + "/");
		}
		// System.out.println(sb.toString());
		// 2.baseUrl 回退n个位置<br>
		String[] resultUrlArr = resultUrl.split("../",-1);
		// https://jsoup.org/../../../serialized-form.html
		// --> [http, /jsoup.o, , , , serialized-form.html]
		// 3.第二步的结果加上 resultUrl ../后面的内容
		return sb.toString() + resultUrlArr[resultUrlArr.length - 1];
	}

	public static boolean isBlank(String str) {
		if (str == null || str.trim().equals(""))
			return true;
		return false;
	}

}
