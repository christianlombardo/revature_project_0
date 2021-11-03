package com.company;

import com.company.CMLibraries.RandomStringCML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        preparedStatement.setFloat(1, account.getBalance());
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
    public void updateAccount(Account account) throws SQLException {

    }

    @Override
    public void deleteAccount(Account account) throws SQLException {

    }

    @Override
    public List<Customer> getAllAccounts(Account account) throws SQLException {
        return null;
    }

    @Override
    public Account getAccountByNumber(Account account) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, account.getAccountNumber());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        //id balance account_number active
        account.setId(resultSet.getInt(1));
        account.setBalance(resultSet.getFloat(2));
        account.setActive(resultSet.getBoolean(4));

        return account;
    }

    @Override
    public Account getAccountById(Account account) throws SQLException {
        return null;
    }

    @Override
    public Account customerApplyNewAccountWithStartBalance(float amount) throws SQLException {
        RandomStringCML randomStringCML = new RandomStringCML();
        String newAccountNumber = randomStringCML.getRandomNumber(10);
        String sql = "INSERT INTO accounts (balance, account_number, active) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setFloat(1, amount);
        preparedStatement.setString(2, newAccountNumber);
        preparedStatement.setBoolean(3, true);
        Account account = new Account();
        account.setAccountNumber(newAccountNumber);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("New account " + newAccountNumber + " created with a balance of " + amount);
        }
        else {
            System.out.println("Oops! something went wrong");
            return null;
        }

        return account;

    }
}
