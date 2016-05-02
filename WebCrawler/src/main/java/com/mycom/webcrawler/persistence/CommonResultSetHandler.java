package com.mycom.webcrawler.persistence;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

public class CommonResultSetHandler implements ResultSetHandler<List<Object[]>>{
	@Override
	//返回二维数组
	public List<Object[]> handle(ResultSet rs) throws SQLException {
		
		List<Object[]> result = new ArrayList<>();
		//result[1] = new Object[]{1,2,3};
		int n = 0;
		while(rs.next()){
			ResultSetMetaData meta = rs.getMetaData();
			int cols = meta.getColumnCount();
			Object[] row = new Object[cols];

			for (int i = 0; i < cols; i++) {
				row[i] = rs.getObject(i + 1);
			}
			result.add(row);
		}

		return result;
	}

}
