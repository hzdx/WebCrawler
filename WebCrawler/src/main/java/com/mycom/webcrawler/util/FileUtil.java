package com.mycom.webcrawler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycom.webcrawler.httpclient.HttpClientHolder;

public class FileUtil {
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);

	public static void main(String[] args) throws Exception {
		saveToLocal("aaa","d:/crawler/maven/download.cgi","index.html");
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

	//todo 当path已经存在，并且是一个文件，不是文件夹，这样保存会报错,这里文件和文件夹重名，无法创建文件夹
	public static void saveToLocal(String html, String path, String fileName) throws IOException {
		try {
			File file = new File(path);
			if (!file.exists())
				file.mkdirs();
			if(file.exists() && file.isFile()) file.mkdir();
			//如果file已经存在，并且是文件，创建同名文件夹。
			file = new File(path, fileName);
			// if(fileName.contains("=")) return;
			if (!file.exists()) {
				file.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(html);
				bw.flush();
				bw.close();
			}
			log.info(path + "/" + fileName + " is saved ...");
		} catch (Exception e) {
			log.error("save file failed! path :{},fileName :{}", path, fileName);
		}
	}
}
