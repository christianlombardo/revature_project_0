package com.company;

import com.company.CMLibraries.General;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerDaoImpl implements CustomerDao {

    Connection connection;

    public CustomerDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public boolean addUser(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (name, username, password, active) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getUsername());
        preparedStatement.setString(3, customer.getPassword());
        preparedStatement.setBoolean(4,customer.getActive());
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {

    }

    @Override
    public void deleteCustomer(Customer customer) throws SQLException {

    }

    @Override
    public List<Customer> getCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String username = resultSet.getString(3);
            String password = resultSet.getString(4);
            String type = resultSet.getString(5);
            Customer customer = new Customer(id, name, username, password);
            customers.add(customer);
        }

        return customers;
    }

    @Override
    public Customer getCustomerById(int id) throws SQLException {
        String sql = "SELECT * FROM customers WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Customer customer = new Customer();
        // id | name      | username  | password | type
        customer.setId(resultSet.getInt(1));
        customer.setName(resultSet.getString(2));
        customer.setUsername(resultSet.getString(3));

        return customer;
    }

    @Override
    public Customer getCustomerByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM customers WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Customer customer = new Customer();
        //  id | name        | username    | password | active
        customer.setId(resultSet.getInt(1));
        customer.setName(resultSet.getString(2));
        customer.setUsername(resultSet.getString(3));
        customer.setActive(resultSet.getBoolean(4));

        return customer;
    }

    @Override
    public boolean customerLogin(Customer customer) throws SQLException {
        System.out.println();
        String sql = "SELECT * FROM customers WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, customer.getUsername());
        preparedStatement.setString(2, customer.getPassword());
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
//                    ", password = " + resultSet.getString(4) +
//                    ", type = " + resultSet.getString(5));
        } else {
            success = false;
        }

        return success;

    }


    @Override
    public void printAllCustomersAccounts(Customer customer) throws SQLException {
        String sql = "SELECT id FROM accounts WHERE customer_id = ? AND status = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customer.getId());
        preparedStatement.setString(2, "approved");
        //System.out.println(preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

    }

}



