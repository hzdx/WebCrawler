package com.mycom.webcrawler.persistence;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDao {
	private static Logger log = LoggerFactory.getLogger(CommonDao.class);

	public static void insert(String sql, Object... args) throws Exception {
		Connection conn = null;
		try {
			conn = DataSource.getConnection();
			QueryRunner run = new QueryRunner();
			if (args == null) {
				run.update(conn, sql);
			} else
				run.update(conn, sql, args);
			log.info("execute sql completed! :{}", sql);
		} catch (Exception e) {
			log.error("exception during execute sql :{}", e.getMessage());
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static void insert(String sql) throws Exception {
		insert(sql, (Object[]) null);
	}

	public static void batchInsert(String sql, Object[][] args) throws Exception {
		Connection conn = null;
		try {
			conn = DataSource.getConnection();
			conn.setAutoCommit(false);
			QueryRunner run = new QueryRunner();
			run.batch(conn, sql, args);
			conn.commit();
			log.info("execute batch sql success :{}", sql);
		} catch (Exception e) {
			log.error("exception during execute batch sql :{}", e.getMessage());
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static List<Object[]> select(String sql) throws Exception {
		Connection conn = null;
		try {
			conn = DataSource.getConnection();
			QueryRunner run = new QueryRunner();
			List<Object[]> result = run.query(conn, sql, //
					new CommonResultSetHandler());
			return result;
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	public static void main(String[] args) throws Exception {
		// String sql = "INSERT INTO aaa (aa,id) VALUES (?,?)";
		// Object[][] arg = {{"vvvv",12},{"ggg",13},{"ccc",14},{"hhhh",15}};
		// List<Object[]> result = new CommonDao().select("SELECT * FROM
		// prop_url");
		// if(result.size()>0){
		// for(Object[] objs:result){
		// for(Object obj:objs) System.out.print(obj +" |");
		// System.out.println();
		// }
		// }
		new CommonDao().insert("INSERT INTO prop_url (url,id) VALUES (?,?)", "abc", 11);
		// new CommonDao().batchInsert(sql, arg);
	}
}
