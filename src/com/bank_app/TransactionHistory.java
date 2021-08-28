package com.bank_app;

import java.io.Serializable;

public class TransactionHistory implements Serializable {
    protected int accNo1;
    protected String name1;
    protected String type1;
    protected int amount1;
    protected double balance1;
    protected String date1;

    public TransactionHistory(int accNo1, String name1, String type1, int amount1, double balance1, String date1) {
        this.accNo1 = accNo1;
        this.name1 = name1;
        this.type1 = type1;
        this.amount1 = amount1;
        this.balance1 = balance1;
        this.date1 = date1;
    }

    @Override
    public String toString() {
        return "AccountNo=" + accNo1 +
                ", Name='" + name1 + '\'' +
                ", Transaction Type='" + type1 + '\'' +
                ", Amount=" + amount1 +
                ", Balance=" + balance1 +
                ", Date of Transaction='" + date1 + '\'';
    }
}
