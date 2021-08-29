package com.bank_app;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class Main_App {
    public static void main(String[] args) {
        int choice = -1;
        Scanner sc = new Scanner(System.in);    // For integer
        Scanner sc1 = new Scanner(System.in);   // For string

        ArrayList<NewAccount> customerList = new ArrayList<>();     // New customers arraylist
        ArrayList<TransactionHistory> TransactionList = new ArrayList<>();     // Transaction history arraylist


        // File Operation
        File newCustomer = new File("Customers.txt");
        File historyFile = new File("TransactionHistory.txt");
        ObjectOutputStream oos;
        ObjectInputStream ois;

        // Store Customer file data in arraylist
        if (newCustomer.isFile() && historyFile.isFile()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(newCustomer));
                customerList = (ArrayList<NewAccount>) ois.readObject();
                ois.close();

                ois = new ObjectInputStream(new FileInputStream(historyFile));
                TransactionList = (ArrayList<TransactionHistory>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        else
        {
            System.out.println("File not found!");
        }

        System.out.println("\n================ Welcome to XYZ bank ================\n");

        do {
            System.out.println("-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o");
            System.out.println("*********************** Menu ***********************");
            System.out.println("-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o");
            System.out.println("1. NEW ACCOUNT");
            System.out.println("2. CUSTOMER DETAILS");
            System.out.println("3. SEARCH CUSTOMER");
            System.out.println("4. DEPOSIT AMOUNT");
            System.out.println("5. WITHDRAW AMOUNT");
            System.out.println("6. TRANSACTION HISTORY");
            System.out.println("0. EXIT");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                // Add new customer
                case 1:
                    if (newCustomer.isFile()) {
                        System.out.println("Enter how many customers you want to add: ");
                        int num = sc.nextInt();

                        for (int i = 0; i < num; i++) {
                            System.out.println("Enter details of customer " + (i + 1));
                            System.out.print("Enter account number: ");
                            int accNo = sc.nextInt();
                            System.out.print("Enter customer name: ");
                            String name = sc1.nextLine();
                            System.out.print("Enter contact number: ");
                            String contact = sc1.nextLine();
                            System.out.print("Enter email address: ");
                            String email = sc1.nextLine();
                            System.out.print("Enter initial deposit amount(Min. Rs. 1000): ");
                            int balance = sc.nextInt();

                            // Date
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            String date = dtf.format(now);

                            // Check initial amt is more 1000 or not
                            if (balance >= 1000) {
                                customerList.add(new NewAccount(accNo, name, contact, email, balance, date));
                                if (i != (num - 1)) {
                                    System.out.println("================================================================");
                                }
                            }

                            else {
                                System.out.println("Minimum balance Rs. 1000 required");
                                break;
                            }
                        }

                        // Upload new customer data into customer file
                        try {
                            oos = new ObjectOutputStream(new FileOutputStream(newCustomer));
                            oos.writeObject(customerList);
                            System.out.println("========================================================================");
                            System.out.println("Details updated successfully");
                            oos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    else {
                        System.out.println("File not found!");
                    }
                    break;

                // View customer Details
                case 2:
                    if (newCustomer.isFile()) {

                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                        for (NewAccount newAcc : customerList) {
                            System.out.println(newAcc);
                        }
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                    }

                    else {
                        System.out.println("File not found!");
                    }
                    break;

                // Search customer account
                case 3:
                    if (newCustomer.isFile()) {
                        int accNo;
                        boolean found = false;
                        System.out.print("Enter account number: ");
                        accNo = sc.nextInt();

                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                        for (NewAccount custDetail : customerList) {
                            if (custDetail.accountNo == accNo) {
                                System.out.println(custDetail);
                                found = true;
                            }
                        }

                        if (!found) {
                            System.out.println("Not found any account by this account number");
                        }
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");



                    }

                    else {
                        System.out.println("File not found!");
                    }

                    break;

                // Deposit amt
                case 4:
                    if (newCustomer.isFile() && historyFile.isFile()) {

                        boolean found = false;      // Flag for matching accNo found or not
                        int amt;                    // Deposit amt
                        double newBalance;          // balance after deposit
                        String type = "Credited";   // Transaction type

                        System.out.print("Enter account number: ");
                        int accNo = sc.nextInt();

                        ListIterator<NewAccount> li = customerList.listIterator();
                        while(li.hasNext()) {
                            NewAccount na = li.next();
                            if (na.accountNo == accNo) {
                                System.out.println(na);

                                System.out.print("Enter amount you want to deposit: ");
                                amt = sc.nextInt();

                                // Adding deposit amt to old balance
                                newBalance = na.balance + amt;

                                // Date
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                LocalDateTime now = LocalDateTime.now();
                                String TransDate;
                                TransDate = dtf.format(now);

                                // Updating customer data
                                li.set(new NewAccount(accNo, na.name, na.contactNo, na.email, newBalance, na.date));

                                // Adding transaction data into TransactionList
                                TransactionList.add(new TransactionHistory(accNo, na.name, type, amt, newBalance, TransDate));

                                System.out.println("Rs. "+ amt + " credited to your account successfully");
                                System.out.println("Balance = Rs. " +newBalance);

                                found = true;
                            }
                        }

                        // If customer account found then write data into file
                        if (found) {
                            try {
                                // Upload data into Customers file
                                oos = new ObjectOutputStream(new FileOutputStream(newCustomer));
                                oos.writeObject(customerList);
                                oos.close();

                                oos = new ObjectOutputStream(new FileOutputStream(historyFile));
                                oos.writeObject(TransactionList);
                                oos.close();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else {
                            System.out.println("Account not found!");
                        }
                    }

                    else {
                        System.out.println("File not found!");
                    }
                    break;

                // Withdraw amt
                case 5:
                    if (newCustomer.isFile() && historyFile.isFile()) {
                        boolean found = false;      // Flag for matching accNo found or not
                        int withAmt;                    // Withdrawal amt
                        double newBalance;          // balance after withdrawal
                        String type = "Debited";   // Transaction type

                        System.out.print("Enter account number: ");
                        int accNo = sc.nextInt();

                        ListIterator<NewAccount> li = customerList.listIterator();
                        while(li.hasNext()) {
                            NewAccount na = li.next();
                            if (na.accountNo == accNo) {
                                System.out.println(na);

                                System.out.print("Enter amount you want to withdraw: ");
                                withAmt = sc.nextInt();

                                if(na.balance - withAmt < 0) {
                                    System.out.println("Insufficient balance");
                                }

                                else {
                                    // Withdrawal amt - balance
                                    newBalance = na.balance - withAmt;

                                    // Date
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                    LocalDateTime now = LocalDateTime.now();
                                    String TransDate;
                                    TransDate = dtf.format(now);

                                    // Updating customer data
                                    li.set(new NewAccount(accNo, na.name, na.contactNo, na.email, newBalance, na.date));

                                    // Adding transaction data into TransactionList
                                    TransactionList.add(new TransactionHistory(accNo, na.name, type, withAmt, newBalance, TransDate));

                                    System.out.println("Rs. "+ withAmt + " debited from your account successfully");
                                    System.out.println("Balance = Rs. " + newBalance);

                                    found = true;
                                }
                            }
                        }

                        // If customer account found then write data into file
                        if (found) {
                            try {
                                // Upload data into Customers file
                                oos = new ObjectOutputStream(new FileOutputStream(newCustomer));
                                oos.writeObject(customerList);
                                oos.close();

                                oos = new ObjectOutputStream(new FileOutputStream(historyFile));
                                oos.writeObject(TransactionList);
                                oos.close();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        else {
                            System.out.println("Account not found!");
                        }

                    }
                    else {
                        System.out.println("File not found!");
                    }
                    break;

                // Display Transaction History
                case 6:
                    if (historyFile.isFile()) {
                        int accNum;
                        boolean found = false;
                        System.out.print("Enter account number: ");
                        accNum = sc.nextInt();

                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                        for (TransactionHistory custHis : TransactionList) {
                            if (custHis.accNo1 == accNum) {
                                System.out.println(custHis);
                                found = true;
                            }
                        }

                        if (!found) {
                            System.out.println("Not found any transaction by this account number");
                        }
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                    }

                    else {
                        System.out.println("File not found!");
                    }

                    break;
            }

        } while(choice!=0);

    }
}
