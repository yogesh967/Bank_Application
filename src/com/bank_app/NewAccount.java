package com.bank_app;

import java.io.Serializable;

public class NewAccount implements Serializable {
    protected int accountNo;
    protected String name;
    protected String contactNo;
    protected String email;
    protected double balance;
    protected String date;

    public NewAccount(int accountNo, String name, String contactNo, String email, double balance, String date) {
        this.accountNo = accountNo;
        this.name = name;
        this.contactNo = contactNo;
        this.email = email;
        this.balance = balance;
        this.date = date;
    }

    @Override
    public String toString() {
        return  "AccountNo=" + accountNo +
                ", Name='" + name + '\'' +
                ", ContactNo='" + contactNo + '\'' +
                ", Email='" + email + '\'' +
                ", Balance=" + balance +
                ", Date='" + date + '\'';
    }
}


