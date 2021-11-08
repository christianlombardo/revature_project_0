package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao {

    Connection connection;

    public EmployeeDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }


    @Override
    public boolean approveAccount(Employee employee, Account account) throws SQLException {
        return false;
    }

    @Override
    public void viewAllCustomersAccounts(Account account) throws SQLException {

    }

    @Override
    public void viewAllTransactions(Customer customer) throws SQLException {

    }

    @Override
    public boolean employeeLogin(Employee employee) throws SQLException {
        System.out.println();
        String sql = "SELECT * FROM employees WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, employee.getUsername());
        preparedStatement.setString(2, employee.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();

        boolean success = false;
        if (resultSet.next()) {
            success = true;
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String username = resultSet.getString(3);
            String password = resultSet.getString(4);

            System.out.println("Hello " + resultSet.getString(2) + " welcome back!");//  +
//                    ", username = " + resultSet.getString(3) +
//                    ", password = " + resultSet.getString(4)
        } else {
            success = false;
        }

        return success;
    }

    @Override
    public Employee getEmployeeByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM employees WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Employee employee = new Employee();
        //  id | name        | username    | password | active
        employee.setId(resultSet.getInt(1));
        employee.setName(resultSet.getString(2));
        employee.setUsername(resultSet.getString(3));

        return employee;
    }
}
