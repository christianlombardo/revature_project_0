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
            System.out.println("User saved");
        }
        else {
            System.out.println("Oops! something went wrong");
            return false;
        }

        return true;

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
            customer = new Customer(id, name, username, password);
            System.out.println("Hello " + resultSet.getString(2) + " welcome back!");//  +
//                    ", username = " + resultSet.getString(3) +
//                    ", password = " + resultSet.getString(4) +
//                    ", type = " + resultSet.getString(5));
        } else {
            success = false;
        }

        return success;

        }

    public void customerPortal(Scanner scanner, Customer customer) {
        AccountDao accountDao = AccountDaoFactory.getAccountDao();

        System.out.println();
        System.out.println("    * Press 1: Apply for a new account.");
        System.out.println("    * Press 2: View all your accounts.");
        System.out.println("    * Press 3: View Balance");
        System.out.println("    * Press 4: Make a withdrawal");
        System.out.println("    * Press 5: Make a deposit");
        System.out.println("    * Press 6: Transfer To Another Account");
        System.out.println("    * Press 7: Exit");

//      • * As a customer, I can apply for a new bank account with a starting balance.
//      • * As a customer, I can view the balance of a specific account.
//      • * As a customer, I can make a withdrawal or deposit to a specific account.
//      • * As a customer, I can post a money transfer to another account.
//          ▪ It is possible the other account can be owned by a different customer.
//      • * As a customer, I can accept a money transfer from another account.


        String accountNumber;

        boolean customerportal = true;
        while (customerportal) {
            String input = scanner.next();
            if (General.isStringInt(input)) {
                switch (Integer.parseInt(input)) {
                    case 1:
                        // Apply for a new account.
                        System.out.println("You are requesting a new account.");
                        System.out.println("Enter the amount you want to deposit in your new account.");
                        float deposit = scanner.nextFloat();
                        try {
                            accountDao.customerApplyNewAccountWithStartBalance(deposit);
                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        // View all logged in customer's accounts
                        try {
                            this.printAllCustomersAccounts(customer);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        // View Balance
                        System.out.println("Enter your account number");
                        accountNumber = scanner.next();
                        Account account = new Account();
                        account.setAccountNumber(accountNumber);
                        try {
                            account = accountDao.getAccountByNumber(account);
                            System.out.println("Your account " + account.getAccountNumber() + " has a balance of $" + account.getBalance());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.print("Select what would you like to do next.... ");
                        break;
                    case 4:
                        // Make a withdrawal
                        break;
                    case 5:
                        // Make a deposit
                        break;
                    case 6:
                        // Transfer To Another Account
                        break;
                    case 7:
                        // Exit
                        customerportal = false;
                        break;
                }
            }
            else {
                System.out.println("Please enter correct entry. Choose between 1 to 4");
            }
        }
    }

    @Override
    public void printAllCustomersAccounts(Customer customer) throws SQLException {
        String sql = "SELECT accounts.account_number FROM accounts " +
                        "INNER JOIN registered ON accounts.id = registered.account_id " +
                        "WHERE registered.customer_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customer.getId());
        //System.out.println(preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

    }

}



