package com.company;

public class EmployeeDaoFactory {

    private static EmployeeDao dao;

    private EmployeeDaoFactory() {
    }

    public static EmployeeDao getUserDao () {
        if (dao == null) {
            dao = new EmployeeDaoImpl();
        }

        return dao;
    }
}
