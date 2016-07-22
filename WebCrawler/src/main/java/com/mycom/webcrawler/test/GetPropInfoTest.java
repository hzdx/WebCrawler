package com.mycom.webcrawler.test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mycom.webcrawler.extractor.PropertyExtractor;
import com.mycom.webcrawler.httpclient.HttpUtil;
import com.mycom.webcrawler.model.Property;
import com.mycom.webcrawler.persistence.PropertyService;

public class GetPropInfoTest {
	// 根据获得的url，收集房源信息。问题：多次请求后，需要输入图片验证码才能访问网页.
	public static void main(String[] args) throws Exception {
		List<String> urlStrList = PropertyService.selectAllUrl();

		final PropertyExtractor extractor = new PropertyExtractor();
		int threadNum = 8;
		ExecutorService service = Executors.newFixedThreadPool(threadNum);
		int part = urlStrList.size() % threadNum == 0 ? urlStrList.size() / threadNum
				: urlStrList.size() / threadNum + 1;
		for (int i = 0; i < threadNum; i++) {
			int startIndex = part * i;
			int endIndex = part * (i + 1);
			if (endIndex > urlStrList.size())
				endIndex = urlStrList.size();
			final List<String> subList = urlStrList.subList(startIndex, endIndex);
			Runnable runner = new Runnable() {
				@Override
				public void run() {
					for (String url : subList) {
						try {
							String html = HttpUtil.fetchUrl(url);
							Property property = extractor.extract(url, html);
							PropertyService.saveProperty(property);
						} catch (Exception e) {
						}

					}
				}

			};
			service.submit(runner);

		}

		service.shutdown();

	}
}
