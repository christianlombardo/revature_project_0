package com.company;

public class Account {
    private int id;
    private String accountNumber;
    private float balance;
    private boolean pending;

    public Account () {}

    public Account(int id, String accountNumber, float balance, boolean pending) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pending = pending;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String account_number) {
        this.accountNumber = account_number;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return pending;
    }

    public void setActive(boolean pending) {
        this.pending = pending;
    }
}
