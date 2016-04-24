package com.mycom.webcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.mycom.webcrawler.httpclient.HttpClientHolder;

public class FileUtil {

	public static void main(String[] args) throws Exception {
		String html = HttpClientHolder.fetchUrl("http://www.oschina.net/");
		// saveToLocal(html,"d:/crawler/","osc1.html");
		saveFile(html, "d:/crawler/osc2.html");

	}

	public static void saveFile(String html, String filePath) throws IOException {

		if (!((filePath.endsWith(".html") || filePath.endsWith(".js") || filePath.endsWith(".css")))) {
			// 文件夹类型
			if (filePath.lastIndexOf("/") == filePath.length() - 1) {
				// 最后一个字符是"/"
				filePath = filePath + "index.html";
			} // 最后一个字符不是"/"
			else
				filePath = filePath + "/index.html";
		}
		int n = filePath.lastIndexOf("/");
		String path = filePath.substring(0, n);
		String fileName = filePath.substring(n + 1, filePath.length());
		saveToLocal(html, path, fileName);
	}

	public static void saveToLocal(String html, String path, String fileName) throws IOException {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		file = new File(path, fileName);
		if(fileName.contains("=")) return;
		if (!file.exists()){
			file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(html);
			bw.flush();
			bw.close();
		}
		System.out.println(path + "/" + fileName + " is saved ...");
	}
}
