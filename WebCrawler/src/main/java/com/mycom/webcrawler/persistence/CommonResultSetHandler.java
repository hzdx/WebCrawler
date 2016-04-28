package com.mycom.webcrawler.persistence;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

public class CommonResultSetHandler implements ResultSetHandler<Object[]>{
	@Override
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

}
