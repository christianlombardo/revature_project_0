package com.company;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDao {

    boolean approveAccount(Employee employee, Account account) throws SQLException;

    void viewAllCustomersAccounts(Account account) throws SQLException;

    void viewAllTransactions(Customer customer) throws SQLException;

    boolean employeeLogin(Employee employee) throws SQLException;

    Employee getEmployeeByUsername(String username) throws SQLException;

}
