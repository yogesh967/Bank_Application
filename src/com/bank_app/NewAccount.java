package com.bank_app;

import java.io.Serializable;

public class NewAccount implements Serializable {
    @Override
    public String toString() {
        return
                "accountNo=" + accountNo +
                ", name='" + name + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance;
    }

    private int accountNo;
    private String name;
    private String contactNo;
    private String email;
    private double balance;

    public NewAccount(int accountNo, String name, String contactNo, String email, double balance) {
        this.accountNo = accountNo;
        this.name = name;
        this.contactNo = contactNo;
        this.email = email;
        this.balance = balance;
    }



}


