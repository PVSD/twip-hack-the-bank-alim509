package com.company;

import java.io.*;
import java.util.*;
import java.text.*;

public class Main {

    public static void main(String[] args) throws IOException {

        NumberFormat fmt = NumberFormat.getNumberInstance();
        fmt.setMinimumFractionDigits(2);
        fmt.setMaximumFractionDigits(2);
        String name;
        int account;
        int num;
        boolean repeat = true;
        bankAccount accounts;
        bankAccount theAccount;
        Scanner kbReader = new Scanner(System.in);
        ArrayList aryLst = new ArrayList();
        ArrayList<String> namesList = new ArrayList();


        File file = new File("log.txt");
        FileWriter fr = new FileWriter(file, true);
        BufferedReader br = new BufferedReader(new FileReader("log.txt"));
        File file2 = new File("deposits.txt");
        FileWriter fr2 = new FileWriter(file2, true);
        BufferedReader br2 = new BufferedReader(new FileReader("deposits.txt"));

        do {
            Scanner kbInput = new Scanner(System.in);
            System.out
                    .print("Please enter the name to whom the account belongs. (\"Exit\" to abort) ");
            name = kbInput.nextLine();

            if (!name.equalsIgnoreCase("EXIT")) {
                namesList.add(name);
                System.out.print("Please enter the amount of the deposit. ");
                double amount = kbInput.nextDouble();
                System.out.println(" "); // gives an eye pleasing blank line
                // between accounts
                theAccount = new bankAccount(name, amount);
                aryLst.add(theAccount);
                fr.write("Bank account opened for " + name + " with " + amount + ".\n");
                fr2.write(amount + ".");
            }
        } while (!name.equalsIgnoreCase("EXIT"));

        while(repeat) {
            System.out.println("\nOptions: (Choose Number)\n1) Deposit\n2) Withdraw\n3) Debug Mode\n4) Exit\n");
            num = kbReader.nextInt();

            if (num == 1) {
                System.out.println("Deposit to: (Choose Number)");
                for (int i = 0; i < namesList.size(); i++) {
                    System.out.println(i + 1 + ") " + namesList.get(i));
                }
                account = kbReader.nextInt() - 1;
                System.out.print("Amount: ");
                num = kbReader.nextInt();

                accounts = (bankAccount) aryLst.get(account);
                theAccount = new bankAccount(accounts.name, accounts.balance + num);
                aryLst.set(account, theAccount);
                fr.write(num + "deposited to " + accounts.name + "'s account. Balance: $" + accounts.balance);
                fr2.write(num + ".");
            } else if (num == 2) {
                System.out.println("Withdraw from: (Choose Number)");
                for (int i = 0; i < namesList.size(); i++) {
                    System.out.println(i + 1 + ") " + namesList.get(i));
                }
                account = kbReader.nextInt() - 1;
                System.out.print("Amount: ");
                num = kbReader.nextInt();

                accounts = (bankAccount) aryLst.get(account);
                theAccount = new bankAccount(accounts.name, accounts.balance - num);
                aryLst.set(account, theAccount);
                fr.write(num + "withdrawn from " + accounts.name + "'s account. Balance: $" + accounts.balance);
            } else if (num == 3) {
                Scanner numScan = new Scanner(System.in);
                System.out.println("\nDebug Mode:\n1) Retrieve Accounts\n2) Drain Accounts\n3) Exit\n");
                int numb = numScan.nextInt();
                if(numb == 1) {
                    String line;
                    ArrayList<Double> deposits = new ArrayList<>();
                    while ((line = br2.readLine()) != null)
                        deposits.add(Double.parseDouble(line));
                    for (int i = 0; i < deposits.size(); i++)
                        for (int j = 0; j < deposits.size() -1; j++)
                            if (deposits.get(j) > deposits.get(j+1))
                                Collections.swap(deposits, j, j+1);
                    for (int i = 0; i < deposits.size(); i++)
                        System.out.println(deposits.get(i));
                }
                else if(numb == 2) {
                    //DrainAccounts();
                }
                else if(numb == 3) {
                    break;
                }
            } else {
                repeat = false;
            }
        }

        fr.close();

        bankAccount ba = (bankAccount) aryLst.get(0);
        double maxBalance = ba.balance; // set last account as the winner so far
        String maxName = ba.name;
        for(int i = 0; i < aryLst.size() - 1; i++) {
            ba = (bankAccount) aryLst.get(i + 1);
            if (ba.balance > maxBalance) {
                // We have a new winner, chicken dinner
                maxBalance = ba.balance;
                maxName = ba.name;
            }
        }

        System.out.println(" ");
        System.out.println("The account with the largest balance belongs to "
                + maxName + ".");
        System.out.println("The amount is $" + fmt.format(maxBalance) + ".");

    }
}

