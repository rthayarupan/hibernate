package com.thayarupan.hibernate.advance.query.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.thayarupan.hibernate.advance.query.entity.Item;

public class QueryReturnWork implements org.hibernate.jdbc.ReturningWork<Item> {
	final protected Long itemId;

	public QueryReturnWork(Long itemId) {
		this.itemId = itemId;
	}

	@Override
	public Item execute(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Item item = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM item WHERE id=?");
			preparedStatement.setLong(1, itemId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String name = resultSet.getString("name");
				// BigDecimal amount = resultSet.getBigDecimal("amount");
				item = new Item(name);
				item.setId(itemId);
				 
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
		return item;
	}

}
