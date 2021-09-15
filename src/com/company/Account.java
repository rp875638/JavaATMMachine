package com.company;

import java.util.ArrayList;

public class Account {
    /**
     * The name of the account.
     */
    private String name;

    /**
     * The current balance of the account.
     */
    private double balance;

    /**
     * The account ID number.
     */
    private String uuid;

    /**
     * The User Object that owns this account.
     */
    private User holder;

    /**
     * The list of transaction for this account.
     */
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank theBank){
        // set the account name
        this.name = name;
        this.holder = holder;

        // get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        // init transactions
        this.transactions = new ArrayList<>();
    }

    /**
     * Get the account ID
     * @return return UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Get summary line for the account
     * @return the string
     */
    public String getSummaryLine(){
        // get the balance
        double balance = this.getBalance();

        //format the the summary line depending on the whether the balance is negative
        if(balance == 0){
            return String.format("%s : $%.02f : %s",this.uuid, balance,this.name);
        }
        else {
            return String.format("%s : $(%.02f) : %s",this.uuid, balance,this.name);
        }
    }

    /**
     * Get the balance of this account by adding the amount of the transaction
     * @return the balance value.
     */
    public double getBalance(){
        double balance = 0;
        for(Transaction t: this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }

    /**
     * Print the transaction history of the account
     */
    public void printTransactionHistory(){
        System.out.printf("\nTransaction history for account %s\n",this.uuid);
        for (int t = this.transactions.size()-1; t>=0; t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Add a new transaction in this account
     * @param amount the amount transaction
     * @param memo the transaction memo
     */
    public void addTransaction(double amount, String memo){
        // create new transaction object and add it our list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }

}
