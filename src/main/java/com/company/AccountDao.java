package com.company;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {

    boolean addAccount(Account account) throws SQLException;

    void updateAccount(Account account) throws SQLException;

    void deleteAccount(Account account) throws SQLException;

    List<Customer> getAllAccounts(Account account) throws SQLException;

    Account getAccountByNumber(Account account) throws SQLException;

    Account getAccountById(Account account) throws SQLException;

    Account customerApplyNewAccountWithStartBalance(float amount) throws SQLException;

}
