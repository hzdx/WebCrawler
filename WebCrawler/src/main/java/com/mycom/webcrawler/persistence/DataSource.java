package com.mycom.webcrawler.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {
	private static BasicDataSource dataSource;
	private DataSource(){		
	}
	
	// 初始化dataSource
	private synchronized static void  initDataSource() throws IOException{
		if (dataSource == null) {
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
		}
		
	}

	public static BasicDataSource get() throws Exception {
		if (dataSource == null) {
			initDataSource();
		}
		return dataSource;
	}
	
	public static Connection getConnection() throws Exception{
		return get().getConnection();
	}
	
	public static void close() throws SQLException {
		if (dataSource != null)
			dataSource.close();
	}

}
