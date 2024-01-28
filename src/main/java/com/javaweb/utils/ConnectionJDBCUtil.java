package com.javaweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
public class ConnectionJDBCUtil {
	@Value("${spring.datasourse.url}")
	static String DB_URL;
	@Value("${spring.datasourse.username}")
	static String USER;
	@Value("${spring.datasourse.password}")
	static String PASS;
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
