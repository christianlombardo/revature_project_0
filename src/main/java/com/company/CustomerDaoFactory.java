package com.company;

public class CustomerDaoFactory {

    private static CustomerDao dao;

    private CustomerDaoFactory() {
    }

    public static CustomerDao getUserDao () {
        if (dao == null) {
            dao = new CustomerDaoImpl();
        }

        return dao;
    }
}
