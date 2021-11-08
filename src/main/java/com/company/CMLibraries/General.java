package com.company.CMLibraries;

import java.util.Scanner;

final public class General {

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static double requestPositiveAmount(Scanner scanner, double amount) {
        boolean withdrawflag = true;
        double userAmount = 0;
        while (withdrawflag) {
            System.out.println("Please enter a valid amount.");
            userAmount = scanner.nextDouble();
            if (userAmount > 0)
                withdrawflag = false;
        }

        return userAmount;

    }


    // enter a valid integer value
    public static int requestInt(String amount, Scanner scanner) {
        int amountInt = 0;
        boolean notInt = true;
        while (notInt) {
            if (isStringInt(amount)) {
                amountInt = Integer.parseInt(amount);
                notInt = false;
            } else {
                System.out.println("Enter a valid integer amount");
                amount = scanner.next();
            }
        }

        return amountInt;
    }


    // enter a valid double value
    public static double requestDouble(String amount, Scanner scanner) {
        double amountDouble = 0;
        boolean notdouble = true;
        while (notdouble) {
            if (isStringDouble(amount)) {
                amountDouble = Double.parseDouble(amount);
                notdouble = false;
            } else {
                System.out.println("Enter a valid decimal amount");
                amount = scanner.next();
            }
        }

        return  amountDouble;
    }
}



