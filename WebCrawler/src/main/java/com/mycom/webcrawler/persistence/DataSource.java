package com.mycom.webcrawler.persistence;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {
	private static BasicDataSource dataSource;

	public static void close() throws SQLException {
		if (dataSource != null)
			dataSource.close();
	}

	public static BasicDataSource getInstance() throws Exception {
		if (dataSource == null) {// 初始化dataSource
			synchronized (DataSource.class) {
				if (dataSource == null) {
					dataSource = new BasicDataSource();
					InputStream is = DataSource.class.getClassLoader().getResourceAsStream("jdbc.properties");
					Properties prop = new Properties();
					prop.load(is);
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
				}
			}
		}

		return dataSource;
	}

}
