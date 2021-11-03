package com.company;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static Connection connection = null;

    private ConnectionFactory () {}

    public static Connection getConnection() {
        if (connection == null) {
            //ResourceBundle bundle = ResourceBundle.getBundle("com/company/dbConfig");
            ResourceBundle bundle = ResourceBundle.getBundle("dbConfig");
            String url = bundle.getString("url");
            String username= bundle.getString("username");
            String password = bundle.getString("password");
            try {
                connection = DriverManager.getConnection(url, username, password);
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return connection;
    }

}
