package com.thayarupan.hibernate.advance.query.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class QueryItemWork implements org.hibernate.jdbc.Work {

	final protected Long itemId;

	public QueryItemWork(Long itemId) {
		this.itemId = itemId;
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM item WHERE id=?");
			preparedStatement.setLong(1, itemId);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString("name");
				//BigDecimal amount = resultSet.getBigDecimal("amount");
				System.out.println("Name: "+name +" amount: "+0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}

}
