package com.company;

import com.company.CMLibraries.General;
import java.sql.*;
import java.util.Scanner;

public class Login {

    Connection connection;

    public Login() {
        this.connection = ConnectionFactory.getConnection();
    }


    public boolean employeeLogin(Customer customer) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND type = 'employee';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        boolean success = false;
        if (resultSet.next()) {
            success = true;
        } else {
            success = false;
        }

        return success;
    }

}
