package com.bank_app;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_App {
    public static void main(String[] args) {
        int choice = -1;
        Scanner sc = new Scanner(System.in);    // For integer
        Scanner sc1 = new Scanner(System.in);   // For string

        ArrayList<NewAccount> customerList = new ArrayList<>();
        // File Operation
        File newCustomer = new File("Customers.txt");
        ObjectOutputStream oos;
        ObjectInputStream ois;

        // Store file data in arraylist
        if (newCustomer.isFile()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(newCustomer));
                customerList = (ArrayList<NewAccount>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        else
        {
            System.out.println("File not found!");
        }

        do {
            System.out.println("====================================================================================================");
            System.out.println("Menu");
            System.out.println("====================================================================================================");
            System.out.println("1. NEW ACCOUNT");
            System.out.println("2. CUSTOMER DETAILS");
            System.out.println("3. DEPOSIT AMOUNT");
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

                            if (balance >= 1000) {
                                customerList.add(new NewAccount(accNo, name, contact, email, balance));
                                if (i != (num - 1)) {
                                    System.out.println("==================================================");
                                }
                            } else {
                                System.out.println("Minimum balance Rs. 1000 required");
                                break;
                            }
                        }

                        try {
                            oos = new ObjectOutputStream(new FileOutputStream(newCustomer));
                            oos.writeObject(customerList);
                            System.out.println("==================================================");
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

                // View Details
                case 2:
                    if (newCustomer.isFile()) {
                        System.out.println("---------------------------------------------------------------------------------------------------");
                        for (NewAccount newAccount : customerList) {
                            System.out.println(newAccount);
                        }
                        System.out.println("---------------------------------------------------------------------------------------------------");
                    }

                    else {
                        System.out.println("File not found!");
                    }
                    break;

                // Deposit amt
                case 3:
                    int amt;
                    System.out.print("Enter amount you want to deposit: ");
                    amt = sc.nextInt();

                    break;
            }

        } while(choice!=0);

    }
}
