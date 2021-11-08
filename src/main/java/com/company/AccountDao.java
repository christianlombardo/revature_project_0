package com.company;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {

    boolean addAccount(Account account) throws SQLException;

    boolean updateAccountBalance(Account account) throws SQLException;

    void deleteAccount(Account account) throws SQLException;

    List<Account> getAllAccounts() throws SQLException;

    //Account getAccountById(Account account) throws SQLException;

    Account getAccountById(Account account) throws SQLException;

    boolean customerApplyNewAccountWithStartBalance(double amount, Customer customer);

    boolean insertTransferToAccount(int from, int to, double transfer_amount) throws SQLException;

    Account getPostedTransfer(Customer customer);

    boolean acceptTransferToAnotherAccount(Account account);

    void updateAccountStatus(int accountId, String status);

    boolean insertTransaction(Account account, Customer customer, String transactiontype, double amount);

    void displayAllTransactions();

}
