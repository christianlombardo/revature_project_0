package com.company;

public class Account {
    private int id;
    private int transferId;
    private String accountNumber;
    private double withdrawalAmount;
    private double depositAmount;
    private double balance;
    private boolean active;
    private double transferAmount;
    private int fromAccountId;
    private int toAccountId;
    private int customerId;
    private String status;



    public Account () {}

    public Account(int id, double balance, boolean active) {
        this.id = id;
        this.balance = balance;
        this.active = active;
    }

    public Account(int id, double balance, int customerId, String status) {
        this.id = id;
        this.balance = balance;
        this.customerId = customerId;
        this.status = status;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transfer_amount) {
        this.transferAmount = transfer_amount;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
