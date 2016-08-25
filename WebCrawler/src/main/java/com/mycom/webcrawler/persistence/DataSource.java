package com.mycom.webcrawler.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {
	private static final BasicDataSource dataSource = initDataSource();

	private DataSource() {
	}

	// 初始化dataSource
	private static BasicDataSource initDataSource() {
		BasicDataSource dataSource = null;
		try {
			dataSource = new BasicDataSource();
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
			Properties prop = new Properties();
			prop.load(is);
			is.close();
			String driverName = prop.getProperty("jdbc.driverName");
			String url = prop.getProperty("jdbc.url");
			String user = prop.getProperty("jdbc.user");
			String password = prop.getProperty("jdbc.password");

			dataSource.setDriverClassName(driverName);
			dataSource.setUrl(url);
			dataSource.setUsername(user);
			dataSource.setPassword(password);

			dataSource.setInitialSize(5); // 初始的连接数；
			dataSource.setMaxTotal(10);
			dataSource.setMinIdle(5);
			dataSource.setMaxIdle(10);
			dataSource.setMaxWaitMillis(30 * 1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataSource;

	}

	public static BasicDataSource get() throws Exception {
		return dataSource;
	}

	public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	public static void close() throws SQLException {
		if (dataSource != null)
			dataSource.close();
	}

}
