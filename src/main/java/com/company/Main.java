package com.company;

import com.company.CMLibraries.General;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main {

//    public static boolean isStringInt(String s) {
//        try {
//            Integer.parseInt(s);
//        }
//        catch (NumberFormatException e) {
//            return false;
//        }
//
//        return true;
//    }

    public static void main(String[] args) {
        CustomerDao customerDao = CustomerDaoFactory.getUserDao();
        Login login = new Login();

        Scanner scanner = new Scanner(System.in);

        boolean run = true;
        boolean menu = true;
        while (run) {
            if (menu) {
                System.out.println("*************************************");
                System.out.println("******* Welcome to CML Bank *********");
                System.out.println("*************************************");
                System.out.println();
                System.out.println("Press 1: Register for an account.");
                System.out.println("Press 2: Customer Login");
                //System.out.println("Press 3: Employee Login");
                System.out.println("Press 4: Exit");
            }
            // display the main menu
            menu = true;

            //Login login;
            int id = 0;
            Customer customer;
            String name;
            String username;
            String password;

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
                            customerDao.addUser(customer);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        // Customer Login
                        try {
                            login = new Login();
                            System.out.println("Enter your username");
                            username = scanner.next();
                            System.out.println("Enter your password");
                            password = scanner.next();
                            customer = new Customer();
                            customer.setUsername(username);
                            customer.setPassword(password);
                            if (customerDao.customerLogin(customer)) {
                                //login.customerPortal(scanner);
                                customer = customerDao.getCustomerByUsername(username);
                                customerDao.customerPortal(scanner, customer);
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
                        break;
                    case 4:
                        // exit
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
