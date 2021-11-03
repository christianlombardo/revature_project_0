package com.company;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public interface CustomerDao {

    boolean addUser(Customer customer) throws SQLException;

    void updateUser(Customer customer) throws SQLException;

    void deleteUser(Customer customer) throws SQLException;

    List<Customer> getUsers() throws SQLException;

    Customer getUserById(int id) throws SQLException;

    boolean customerLogin(Customer customer) throws SQLException;

    void customerPortal(Scanner scanner);


}
