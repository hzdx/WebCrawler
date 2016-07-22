package com.mycom.webcrawler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	private static final String CONNECTOR = "_";
	private static final String HTML_EXT = "html";
	public static final char[] specialChars = { '/', '\\', '<', '>', '|', '?', ':', '*', '"' };

	public static void main(String[] args) throws Exception {
		saveFullName("aaa", "d:/crawler/maven/download.cgi", "index.html");
	}

	@Deprecated
	public static void saveFile(String html, String filePath, boolean addSuffix) throws IOException {
		if (addSuffix) {
			if (!((filePath.endsWith(".html") || filePath.endsWith(".js") || filePath.endsWith(".css")))) {
				// 文件夹类型
				if (filePath.lastIndexOf("/") == filePath.length() - 1) {
					// 最后一个字符是"/"
					filePath = filePath + "index.html";
				} // 最后一个字符不是"/"
				else
					filePath = filePath + "/index.html";
			}
		}
		int n = filePath.lastIndexOf("/");
		String path = filePath.substring(0, n);
		String fileName = filePath.substring(n + 1, filePath.length());
		saveFullName(html, path, fileName);
	}

	public static void saveToLocal(String html, String path, String fileNameHead) throws IOException {
		saveToLocal(html, path, fileNameHead.replaceAll("/", "&"), HTML_EXT);
	}

	public static void saveToLocal(String html, String path, String fileNameHead, String extension) throws IOException {
		try {
			File file = new File(path);
			String fullFileName = null;
			if (!file.exists()) {
				file.mkdirs();
				fullFileName = fileNameHead + "." + extension;
			} else
				fullFileName = detectFileName(file, fileNameHead, extension);

			// 如果file已经存在，并且是文件，不能创建同名文件夹。
			file = new File(path, fullFileName);
			if (!file.exists()) {
				file.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(html);
				bw.flush();
				bw.close();
				log.info(path + "/" + fullFileName + " is saved ...");
			} else
				log.info(path + "/" + fullFileName + " already exists ...");
		} catch (Exception e) {
			log.error("save file failed! path :{},fileName :{}", path, fileNameHead, e.getMessage());
		}
	}

	// 如果index.txt文件已经存在，则生成index_1.txt文件，避免创建重名文件
	public static String detectFileName(File directory, String fileNameHead, String extension) throws IOException {
		String[] allFileInDir = directory.list();
		if (allFileInDir == null)
			throw new IOException("can't list file in :" + directory.getAbsolutePath());

		List<String> fileNameList = Arrays.asList(allFileInDir);
		String firstFile = fileNameHead + "." + extension;
		if (!fileNameList.contains(firstFile)) // 判断是否包含同名文件
			return firstFile;

		int maxFileNameIdx = 0;
		for (String fileName : fileNameList) {
			if (fileName.startsWith(fileNameHead)) {
				if (fileName.equals(firstFile)) {
					continue;
				}
				int underlineIdx = fileName.lastIndexOf(CONNECTOR);
				int dotIdx = fileName.lastIndexOf(".");
				String indexStr = fileName.substring(underlineIdx + 1, dotIdx);
				int index = Integer.parseInt(indexStr);
				if (index > maxFileNameIdx)
					maxFileNameIdx = index;
			}
		}
		int currentIdx = maxFileNameIdx + 1;
		return fileNameHead + CONNECTOR + currentIdx + "." + extension;

	}

	public static void saveFullName(String html, String path, String fileName) throws IOException {
		int dotIdx = fileName.lastIndexOf(".");
		String fileNameHead = fileName.substring(0, dotIdx);
		String extension = fileName.substring(dotIdx + 1);
		saveToLocal(html, path, fileNameHead, extension);
	}
}
