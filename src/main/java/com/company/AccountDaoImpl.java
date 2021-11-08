package com.company;

import com.company.CMLibraries.RandomStringCML;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao{

    Connection connection;

    public AccountDaoImpl () {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public boolean addAccount(Account account) throws SQLException {
        String sql = "insert into account (balance, account_number, pending) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, account.getBalance());
        preparedStatement.setString(2, account.getAccountNumber());
        preparedStatement.setBoolean(3, true);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Account saved");
        }
        else {
            System.out.println("Oops! something went wrong");
            return false;
        }

        return true;
    }

    @Override
    public boolean updateAccountBalance(Account account) throws SQLException {
        String sql = "UPDATE accounts set balance = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, account.getBalance());
        preparedStatement.setInt(2, account.getId());
        int count = preparedStatement.executeUpdate();

        if (count > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void deleteAccount(Account account) throws SQLException {

    }

    @Override
    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT a.id, a.balance, a.customer_id, a.status FROM accounts a";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            double balance = resultSet.getDouble(2);
            int customerId = resultSet.getInt(3);
            String status = resultSet.getString(4);
            Account account1 = new Account(id, balance, customerId, status);
            accounts.add(account1);
        }

        return accounts;

    }

    @Override
    public Account getAccountById(Account account) throws SQLException {
        String sql = "SELECT id, balance, active FROM accounts WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, account.getId());

        //System.out.println(preparedStatement.toString());

        ResultSet resultSet = preparedStatement.executeQuery();


        //resultSet.next();

        //id balance account_number active
        if (resultSet.next()) {
            account.setId(resultSet.getInt(1));
            account.setBalance(resultSet.getDouble(2));
            account.setActive(resultSet.getBoolean(3));
        }
        else {
            return null;
        }

        return account;
    }

    @Override
    public boolean customerApplyNewAccountWithStartBalance(double amount, Customer customer) {
        String sql = "INSERT INTO accounts (balance, customer_id, active) VALUES (?, ?, ?)";
        int count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, customer.getId());
            preparedStatement.setBoolean(3, false);

            count = preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (count > 0) {
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public boolean insertTransferToAccount(int from, int to, double transfer_amount) throws SQLException{
        String sql = "INSERT INTO transfer_to_account (from_account_id, to_account_id, transfer_amount) VALUES (?, ?, ?)";

        int count;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, from);
        preparedStatement.setInt(2, to);
        preparedStatement.setDouble(3, transfer_amount);
        count = preparedStatement.executeUpdate();


        if (count > 0) {
            return true;
//            System.out.println("You have posted a transfer to your account " + to.getId());
        }
        else {
            return false;
//            System.out.println("Oops! something went wrong");
        }

    }

    @Override
    public Account getPostedTransfer(Customer customer) {
        String sql = "SELECT b.id AS transferId, a.id, a.balance, b.from_account_id, b.to_account_id, b.transfer_amount, a.customer_id " +
                        "FROM accounts a " +
                        "INNER JOIN transfer_to_account b ON b.to_account_id = a.id " +
                        "WHERE a.customer_id = ? AND b.approved = ?";

        Account account = new Account();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setInt(2, 0);
            //System.out.println(preparedStatement.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            //while (resultSet.next()) {
            account.setTransferId(resultSet.getInt(1));
            account.setId(resultSet.getInt(2));
            account.setBalance(resultSet.getDouble(3));
            account.setFromAccountId(resultSet.getInt(4));
            account.setToAccountId(resultSet.getInt(5));
            account.setTransferAmount(resultSet.getDouble(6));
            account.setActive(resultSet.getBoolean(7));
//                postedTransfers.add(account);
            //}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return account;

    }

    @Override
    public boolean acceptTransferToAnotherAccount(Account account) {
        String sql = "UPDATE transfer_to_account SET approved = ? WHERE id = ?";
        int count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, account.getTransferId());
            count = preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (count > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void updateAccountStatus(int accountId, String status) {
        String sql = "UPDATE accounts SET status = ? WHERE id = ?";
        int count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, accountId);
            count = preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (count > 0) {
            System.out.println("Account " + accountId + " " + status);
        }
        else {
            System.out.println("Oops! Something went wrong.");
        }
    }

    @Override
    public boolean insertTransaction(Account account, Customer customer, String transactionType, double amount) {
        String sql = "INSERT INTO transactions (account_id, customer_id, transaction_type, amount) VALUES (?, ?, ?, ?)";

        int count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setInt(2, customer.getId());
            preparedStatement.setString(3, transactionType);
            preparedStatement.setDouble(4, amount);
            //System.out.println(preparedStatement.toString());
            count = preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (count > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void displayAllTransactions() {
        // id | transaction_type       | amount   | customer_id | account_id
        String sql = "SELECT id, transaction_type, amount, customer_id, account_id FROM transactions";
        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("Id | Transaction Type | Amount | Customer Id | Account Id");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String transactionType = resultSet.getString(2);
                double amount = resultSet.getDouble(3);
                int customerId = resultSet.getInt(4);
                int accountId = resultSet.getInt(5);
                System.out.println(id + " " + transactionType + " " + amount + " " + customerId + " " + accountId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
