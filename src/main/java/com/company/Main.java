package com.company;

import com.company.CMLibraries.General;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void customerPortal(Scanner scanner, Customer customer, CustomerDao customerDao) {
        AccountDao accountDao = AccountDaoFactory.getAccountDao();

//      • * As a customer, I can apply for a new bank account with a starting balance.
//      • * As a customer, I can view the balance of a specific account.
//      • * As a customer, I can make a withdrawal or deposit to a specific account.
//      • * As a customer, I can post a money transfer to another account.
//          ▪ It is possible the other account can be owned by a different customer.
//      • * As a customer, I can accept a money transfer from another account.

        int accountIdInt;
        Account account;
        Account transferFrom = new Account();
        Account transferTo  = new Account();
        String withdrawAmount;
        String depositAmount;
        double withdrawDouble;
        double depositDouble;
        String accountIdString;

        AccountService accountService = new AccountService();

        NumberFormat df = DecimalFormat.getCurrencyInstance();
        df.setMaximumFractionDigits(2);

        boolean customerportal = true;
        while (customerportal) {
            System.out.println();
            System.out.println("    * Press 1: Apply for a new account.");
            System.out.println("    * Press 2: View all your accounts.");
            System.out.println("    * Press 3: View Balance");
            System.out.println("    * Press 4: Make a withdrawal");
            System.out.println("    * Press 5: Make a deposit");
            System.out.println("    * Press 6: Post a Transfer To Another Account");
            System.out.println("    * Press 7: Accept a Transfer To Another Account");
            System.out.println("    * Press 8: Exit");

            String input = scanner.next();
            if (General.isStringInt(input)) {
                switch (Integer.parseInt(input)) {
                    case 1:
                        // Apply for a new account.
                        System.out.println("You are requesting a new account.");
                        System.out.println("Enter the amount you want to start with in your new account.");
                        double deposit = scanner.nextDouble();
                        if (accountDao.customerApplyNewAccountWithStartBalance(deposit, customer)) {
                            System.out.println("You applied for a new account with a starting balance of " + df.format(deposit) +
                                    " One of our representatives will be going over your new account application. Check back soon to view your new account.");
                        }
                        else {
                            System.out.println("Oops! something went wrong");
                        }
                        break;
                    case 2:
                        // View all logged in customer's accounts
                        try {
                            System.out.println("**** " + customer.getName() + "'s All Active Accounts");
                            customerDao.printAllCustomersAccounts(customer);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        // View Balance
                        account = new Account();

                        // get the valid account number from customer
                        try {
                            account = accountService.getValidAccount("Enter your account number", scanner, account, accountDao);
                            System.out.println("Your account " + account.getId() + " has a balance of " + df.format(account.getBalance()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println();
                        System.out.print("Select what would you like to do next.... ");
                        break;
                    case 4:
                        // Make a withdrawal
                        System.out.println("Enter your account number");
                        accountIdString = scanner.next();
                        accountIdInt = General.requestInt(accountIdString, scanner);

                        System.out.println("Enter your withdrawal amount");
                        withdrawAmount = scanner.next();
                        withdrawDouble = General.requestDouble(withdrawAmount, scanner);

                        // allow user to enter a valid currency amount greater than 0
                        if (withdrawDouble <= 0) {
                            withdrawDouble = General.requestPositiveAmount(scanner, withdrawDouble);
                        }
                        account = new Account();
                        account.setId(accountIdInt);
                        account.setWithdrawalAmount(withdrawDouble);

                        if (accountService.withdraw(account, accountDao, scanner)) {
                            // update transactions table
                            accountDao.insertTransaction(account,customer,"Withdrawal", withdrawDouble);

                            System.out.println("You have withdrawn " + df.format(account.getWithdrawalAmount()) + " from your account " + account.getId());
                            System.out.println("Your new account balance is " + df.format(account.getBalance()));
                        } else {
                            System.out.println("Something went wrong please call the customer service to resolve.");
                        }
                        System.out.println();
                        break;
                    case 5:
                        // Make a deposit
                        System.out.println("Enter your account number");
                        accountIdString = scanner.next();
                        accountIdInt = General.requestInt(accountIdString, scanner);

                        System.out.println("Enter your deposit amount");
                        depositAmount = scanner.next();
                        depositDouble = General.requestDouble(depositAmount, scanner);

                        // allow user to enter a valid currency amount greater than 0
                        if (depositDouble <= 0) {
                            depositDouble = General.requestPositiveAmount(scanner, depositDouble);
                        }
                        account = new Account();
                        account.setId(accountIdInt);
                        account.setDepositAmount(depositDouble);

                        if (accountService.deposit(account, accountDao)) {
                            // update transactions table
                            accountDao.insertTransaction(account,customer,"deposit", depositDouble);

                            System.out.println("You have deposited " + df.format(account.getDepositAmount()) +  " from your account " + account.getId());
                            System.out.println("Your new account balance is " + df.format(account.getBalance()));
                        } else {
                            System.out.println("Something went wrong please call the customer service to resolve.");
                        }
                        break;
                    case 6:
                        //  Post a Transfer To Another Account");
                        try {
                            transferFrom = accountService.getValidAccount("Select the account number you want to transfer from ", scanner, new Account(), accountDao);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Select the amount you want to transfer ");
                        withdrawAmount = scanner.next();
                        withdrawDouble = General.requestDouble(withdrawAmount, scanner);
                        transferFrom.setWithdrawalAmount(withdrawDouble);

                        try {
                            transferTo = accountService.getValidAccount("Select the account number you want to transfer to ", scanner, new Account(), accountDao);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        // make sure user does not enter same account numbers
                        while (transferFrom.getId() == transferTo.getId()) {
                            try {
                                transferTo = accountService.getValidAccount("Please select a different account number you want to transfer to ", scanner, new Account(), accountDao);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                        // get valid withdrawal amount from customer
                        if (withdrawDouble > transferFrom.getBalance()) {
                            withdrawDouble = accountService.getValidWithdrawAmount(withdrawDouble, transferFrom.getBalance(), scanner);
                        }

                        try {
                            if (accountDao.insertTransferToAccount(transferFrom.getId(), transferTo.getId(), withdrawDouble)) {
                                // update transactions table
                                accountDao.insertTransaction(transferFrom,customer,"posted_transfer_from", withdrawDouble);
                                accountDao.insertTransaction(transferTo,customer,"posted_transfer_to", withdrawDouble);

                                System.out.println("You have posted a transfer to your account " + transferTo.getId());
                            }
                            else {
                                System.out.println("Oops! something went wrong");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        // Accept a Transfer to Another Account
                        Account postedTransferAccount = accountDao.getPostedTransfer(customer);
                        System.out.println("From Account " + postedTransferAccount.getFromAccountId());
                        System.out.println("To Account " + postedTransferAccount.getToAccountId());
                        System.out.println("Transfer Amount " + df.format(postedTransferAccount.getTransferAmount()));

                        System.out.println("Do you want to approve transfer?");
                        System.out.println("Select 1 Yes or 2 no");
                        String transferId = scanner.next();

                        Account fromAccount = new Account();
                        fromAccount.setId(postedTransferAccount.getFromAccountId());
                        Account toAccount = new Account();
                        toAccount.setId(postedTransferAccount.getToAccountId());

                        boolean accept = true;
                        while (accept) {
                            if (General.isStringInt(transferId)) {
                                if (transferId.equals("1")) {
                                    System.out.println("You have approved the transfer.");
                                    accountService.postTransfer(postedTransferAccount, accountDao);
                                    // update transactions table
                                    accountDao.insertTransaction(fromAccount,customer,"accepted_transfer_from", postedTransferAccount.getTransferAmount());
                                    accountDao.insertTransaction(toAccount,customer,"accepted_transfer_to", postedTransferAccount.getTransferAmount());
                                } else {
                                    System.out.println("You have denied the transfer.");
                                }
                                accept = false;
                            } else {
                                System.out.println("Please enter correct entry. Choose between 1 or 2");
                                transferId = scanner.next();
                            }
                        }
                        break;
                    case 8:
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

    public static void employeePortal(Scanner scanner, Employee employee, EmployeeDao employeeDao) {
        AccountDao accountDao = AccountDaoFactory.getAccountDao();

//      • * As an employee, I can approve or reject an account.
//      • * As an employee, I can view a customer's bank accounts.
//      • * A an employee, I can view a log of all transactions.

        int accountIdInt;
        String accountIdString;

        NumberFormat df = DecimalFormat.getCurrencyInstance();
        df.setMaximumFractionDigits(2);

        boolean employeeportal = true;
        while (employeeportal) {
            System.out.println();
            System.out.println("    * Press 1: View All Customers Accounts");
            System.out.println("    * Press 2: Approve An Account");
            System.out.println("    * Press 2: Reject an Account");
            System.out.println("    * Press 4: View All Transactions.");
            System.out.println("    * Press 5: Exit");

            String input = scanner.next();
            if (General.isStringInt(input)) {
                switch (Integer.parseInt(input)) {
                    case 1:
                        // View All Customers Accounts
                        List<Account> accounts =  null;
                        try {
                            accounts = accountDao.getAllAccounts();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Account Id | Balance | Customer Id | Active | Status");
                        for(Account currAccount : accounts) {
                            //  a.id, a.balance, a.customer_id, a.status, a.status
                            System.out.println(currAccount.getId() + " " + currAccount.getBalance() + " " + currAccount.getCustomerId() + " " + currAccount.getActive() + " " + currAccount.getStatus());
                        }
                        break;
                    case 2:
                        // Approve An Account
                        System.out.println("Select an account");
                        accountIdString = scanner.next();
                        accountIdInt = General.requestInt(accountIdString, scanner);
                        accountDao.updateAccountStatus(accountIdInt, "approved");
                        break;
                    case 3:
                        // Reject an Account
                        System.out.println("Select an account");
                        accountIdString = scanner.next();
                        accountIdInt = General.requestInt(accountIdString, scanner);
                        accountDao.updateAccountStatus(accountIdInt, "rejected");
                        break;
                    case 4:
                        // View All Transactions
                        accountDao.displayAllTransactions();
                        break;
                    case 5:
                        // Exit
                        employeeportal = false;
                        break;
                    default:

                }
            }
        }
    }


    public static void main(String[] args) {
        CustomerDao customerDao = CustomerDaoFactory.getUserDao();
        EmployeeDao employeeDao = EmployeeDaoFactory.getUserDao();
        Login login = new Login();

        Scanner scanner = new Scanner(System.in);

        boolean run = true;
        boolean menu = true;
        String name;
        String username;
        String password;
        while (run) {
            if (menu) {
                System.out.println("*************************************");
                System.out.println("******* Welcome to CML Bank *********");
                System.out.println("*************************************");
                System.out.println();
                System.out.println("Press 1: Register for an account.");
                System.out.println("Press 2: Customer Login");
                System.out.println("Press 3: Employee Login");
                System.out.println("Press 4: Exit");
            }
            // display the main menu
            menu = true;

            int id = 0;
            Customer customer;

            String input = scanner.next();
            if (General.isStringInt(input)) {
                switch (Integer.parseInt(input)) {
                    case 1:
                        // Register
                        login = new Login();
                        System.out.println("Enter your name");
                        name = scanner.next();
                        System.out.println("Create a username");
                        username = scanner.next();
                        System.out.println("Create a password");
                        password = scanner.next();
                        customer = new Customer();
                        customer.setName(name);
                        customer.setUsername(username);
                        customer.setPassword(password);
                        customer.setActive(false);
                        try {
                            if (customerDao.addUser(customer)) {
                                System.out.println("You have registered for an account. Y0ur account will be activated shortly.");
                                System.out.println();
                            }
                            else {
                                System.out.println("Oops! something went wrong");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        // Customer Login
                        try {
                            System.out.println("Enter your username");
                            username = scanner.next();
                            System.out.println("Enter your password");
                            password = scanner.next();
                            customer = new Customer();
                            customer.setUsername(username);
                            customer.setPassword(password);
                            if (customerDao.customerLogin(customer)) {
                                customer = customerDao.getCustomerByUsername(username);
                                customerPortal(scanner, customer, customerDao);
                            } else {
                                System.out.println();
                                System.out.println("Check your username and password are correct.");
                                System.out.println();
                            }
                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            run = true;
                        }

                        break;
                    case 3:
                        // Employee Login
                        try {
                            System.out.println("Enter your username");
                            username = scanner.next();
                            System.out.println("Enter your password");
                            password = scanner.next();
                            Employee employee = new Employee();
                            employee.setUsername(username);
                            employee.setPassword(password);
                            if (employeeDao.employeeLogin(employee)) {
                                employee = employeeDao.getEmployeeByUsername(username);
                                employeePortal(scanner, employee, employeeDao);
                            } else {
                                System.out.println();
                                System.out.println("Check your username and password are correct.");
                                System.out.println();
                            }
                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            run = true;
                        }
                        break;
                    case 4:
                        // Exit
                        System.out.println("Thank you for using CML Bank.");
                        System.out.println("Exiting...");
                        run = false;
                        break;
                    default:
                        System.out.println("Choose between 1 to 4");
                }
            }
            else {
                System.out.println("Please enter correct entry. Choose between 1 to 4");

                // don't display the main menu user is reentering the correct input.
                menu = false;
            }
        }

    }
}
