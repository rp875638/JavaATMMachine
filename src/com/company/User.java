package com.company;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    /**
    * Fist name of user.
     */
    private String firstName;

    /**
    * The last name of user.
     */
    private String lastName;

    /**
    * The ID number of user.
     */
    private String uuid;

    /**
    * the MD5 hash of the user's pin number.
     */
    private byte[] pinHash;

    /**
     * The list of accounts for this user.
     */
    private ArrayList<Account> accounts;

    /**
     * Create a new user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param pin the user's account pin number
     * @param theBank the Bank object that the user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank theBank){
        //set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin's MD5 hash, rather than original value, security reasons.
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            this.pinHash = messageDigest.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        // get a new, unique universal ID for the user.
        this.uuid = theBank.getNewUserUUID();

        // create empty list of the accounts
        this.accounts = new ArrayList<>();

        // print log message
        System.out.printf("New user %s, %s with ID %s created.\n", firstName, lastName, this.uuid);
    }

    /**
     * Add account for the user
     * @param anAcct the account to add
     */
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    /**
     * Get the user ID
     * @return return UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Check whether a given pin matches the true User pin
     * @param aPin the pin check
     * @return whether the pin is valid or not
     */
    public boolean validatePin(String aPin){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(messageDigest.digest(aPin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    /**
     * Return the user's first name
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Print summaries for the account of this user
     */
    public void printAccountSummery(){
        System.out.printf("\n\n%s accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("%d) %s\n",a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Get the number of account of the user
     * @return the number of accounts
     */
    public int numAccounts(){
        return this.accounts.size();
    }

    /**
     * Print transaction history for a particular account.
     * @param acctIdx the index of the account to use
     */
    public void printAccountTransactionHistory(int acctIdx){
        this.accounts.get(acctIdx).printTransactionHistory();
    }

    /**
     * Get the balance of a particular account
     * @param acctIdx the index of the account use
     * @return the balance of the account
     */
    public double getAccountBalance(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();
    }

    /**
     * Get the UUID of a particular account
     * @param acctIdx the index of the account to use
     * @return the UUID of the account
     */
    public String getAccountUuid(int acctIdx){
        return this.accounts.get(acctIdx).getUuid();
    }

    /**
     * Add transaction to a particular account
     * @param acctIdx the index of the account
     * @param amount the amount of th transaction
     * @param memo the memo of the transaction
     */
    public void addAccountTransaction(int acctIdx, double amount,String memo){
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }


}
