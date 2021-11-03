package com.company;

public class AccountDaoFactory {

//    private static CustomerDao dao;
private static AccountDao accountDao;

    private AccountDaoFactory() {
    }

    public static AccountDao getAccountDao () {
        if (accountDao == null) {
            accountDao = new AccountDaoImpl();
        }

        return accountDao;
    }

//    public static CustomerDao getAccountDao () {
//        if (dao == null) {
//            dao = new CustomerDaoImpl();
//        }
//
//        return dao;
//    }
}
