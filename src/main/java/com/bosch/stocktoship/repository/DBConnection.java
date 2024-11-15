package com.bosch.stocktoship.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SQLException("Unable to load properties file", e);
        }

        String url = properties.getProperty("spring.datasource.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, username, password);
    }
}
