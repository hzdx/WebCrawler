package com.mycom.webcrawler.persistence;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;

public class CommonDao {
	public static void insert(String sql, Object... args) throws Exception {
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getConnection();
			QueryRunner run = new QueryRunner();
			if (args == null) {
				run.update(conn, sql);
			} else
				run.update(conn, sql, args);
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public static void insert(String sql) throws Exception {
		insert(sql, (Object[])null);
	}

	public static void batchInsert(String sql,Object[][] args)throws Exception{
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getConnection();
			conn.setAutoCommit(false);
			QueryRunner run = new QueryRunner();
			run.batch(conn, sql, args);
			conn.commit();
		} finally {
			if (conn != null)
				conn.close();
		}
	}
	
	public static void select(String sql) throws Exception {
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getConnection();
			QueryRunner run = new QueryRunner();
			Object[] result = run.query(conn, sql, //
					new CommonResultSetHandler());
			for (Object obj : result)
				System.out.println(obj);
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	public static void main(String[] args) throws Exception {
		String sql = "INSERT INTO aaa (aa,id) VALUES (?,?)";
		Object[][] arg = {{"vvvv",12},{"ggg",13},{"ccc",14},{"hhhh",15}};
		// new CommonDao().select("SELECT * FROM aaa WHERE id=1");
		//new CommonDao().insert("INSERT INTO aaa (aa,id) VALUES (?,?)","abc",11);
		new CommonDao().batchInsert(sql, arg);
	}
}
