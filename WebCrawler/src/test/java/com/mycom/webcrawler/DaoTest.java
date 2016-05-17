package com.mycom.webcrawler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.mycom.webcrawler.persistence.DataSource;

public class DaoTest {
	public static void testDb() throws Exception {

		ResultSetHandler<Object[]> h = new ResultSetHandler<Object[]>() {
			public Object[] handle(ResultSet rs) throws SQLException {
				if (!rs.next()) {
					return null;
				}

				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				Object[] result = new Object[cols];

				for (int i = 0; i < cols; i++) {
					result[i] = rs.getObject(i + 1);
				}

				return result;
			}
		};

		Connection conn = DataSource.getInstance().getConnection();

		QueryRunner run = new QueryRunner();

		Object[] result = run.query(conn, "SELECT * FROM aaa WHERE id=1", h);

		for (Object obj : result)
			System.out.println(obj);
		
		conn.close();
		//接池中的连接是永远不关的，关掉的话连接就得不到重用，也起不到连接池的作用了。
		//但是就算使用连接池，也必须在代码中调用 con.close()，这个是为了将连接还回到池中去，便于其他用户使用。
		//Connection只是一个接口而已

		DbUtils.close(conn);
	}

	public static void testInsert() throws Exception {
		QueryRunner run = new QueryRunner(DataSource.getInstance());
		int inserts = run.update("INSERT INTO aaa (aa,id) VALUES (?,?)", "ddd Doe", 2);
		
	}

	public static void main(String[] args) throws Exception {
		testDb();
		//testInsert();
	}

}
