package com.company;

import com.company.CMLibraries.General;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class AccountService {

//    DecimalFormat df;
    NumberFormat df = DecimalFormat.getCurrencyInstance();

    AccountService() {
        df.setMaximumFractionDigits(2);

    }


    // To check to make sure user entered a valid account number.
    public Account getValidAccount (String message, Scanner scanner, Account account, AccountDao accountDao) throws SQLException {
        Account result;
        boolean accountNoResult = true;
        int accountIdInt;
        String accountIdString;
        while (accountNoResult) {
            System.out.println(message);
            accountIdString = scanner.next();
            accountIdInt = General.requestInt(accountIdString, scanner);

            account.setId(accountIdInt);
            result = accountDao.getAccountById(account);

            if (result != null) {
                account = result;
                accountNoResult = false;
            }
        }

        return account;
    }

    public double getValidWithdrawAmount(double withdrawAmount, double accountBalance, Scanner scanner) {
        while (withdrawAmount > accountBalance) {
            System.out.println("You have entered more than in your current balance. Please re-enter a withdrawal amount.");
            withdrawAmount = scanner.nextDouble();
        }

        return withdrawAmount;
    }

    public boolean withdraw(Account account, AccountDao accountDao, Scanner scanner) {
        try {
            account = accountDao.getAccountById(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (account.getWithdrawalAmount() > account.getBalance()) {
            account.setWithdrawalAmount(this.getValidWithdrawAmount(account.getWithdrawalAmount(), account.getBalance(), scanner));
        }
        try {
            double newBalance = account.getBalance() - account.getWithdrawalAmount();
            account.setBalance(newBalance);
            if (accountDao.updateAccountBalance(account)) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            return false;
        }

    }

    public boolean withdraw(Account account, AccountDao accountDao) {
        try {
            account = accountDao.getAccountById(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (account.getWithdrawalAmount() > account.getBalance()) {
            return false;
        }
        try {
            double newBalance = account.getBalance() - account.getWithdrawalAmount();
            account.setBalance(newBalance);
            if (accountDao.updateAccountBalance(account)) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            return false;
        }

    }

    public boolean deposit(Account account, AccountDao accountDao) {
        try {
            account = accountDao.getAccountById(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        double newBalance = account.getBalance() + account.getDepositAmount();
        account.setBalance(newBalance);

        try {
            if (accountDao.updateAccountBalance(account)) {
                return true;
            }
            else {
                return false;
            }

        } catch (SQLException e) {
//            e.printStackTrace();
            return false;
        }
    }

    public void postTransfer(Account account, AccountDao accountDao) {
        if (accountDao.acceptTransferToAnotherAccount(account)) {

            // Withdraw From the Account
            Account fromAccount = new Account();
            fromAccount.setId(account.getFromAccountId());
            fromAccount.setWithdrawalAmount(account.getTransferAmount());
            if (this.withdraw(fromAccount, accountDao)) {
                // Deposit To the Account
                Account toAccount = new Account();
                toAccount.setId(account.getToAccountId());
                toAccount.setDepositAmount(account.getTransferAmount());
                this.deposit(toAccount, accountDao);
                System.out.println("You have successfully posted a transfer to your account " + account.getId());
            } else {
                System.out.println("This is not a valid transaction.");
            }

        }
        else {
            System.out.println("Oops! something went wrong");
        }

    }

}
