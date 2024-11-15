package com.bosch.stocktoship.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {

	/*
	 * AUTHOR: CHARUL SAINI (SIC2COB) 
	 * AUTHOR: DAFEDAR MUJAHID
	 */
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "7890";
		return DriverManager.getConnection(url, user, password);
	}

}
