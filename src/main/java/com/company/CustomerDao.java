package com.company;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public interface CustomerDao {

    boolean addUser(Customer customer) throws SQLException;

    void updateCustomer(Customer customer) throws SQLException;

    void deleteCustomer(Customer customer) throws SQLException;

    List<Customer> getCustomers() throws SQLException;

    Customer getCustomerById(int id) throws SQLException;

    Customer getCustomerByUsername(String username) throws SQLException;

    boolean customerLogin(Customer customer) throws SQLException;

    void customerPortal(Scanner scanner, Customer customer);

    void printAllCustomersAccounts(Customer customer) throws SQLException;


}
