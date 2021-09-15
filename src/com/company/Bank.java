package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    /**
     * Bank's name.
     */
    private String name;

    /**
     * Bank's user list.
     */
    private ArrayList<User> users;

    /**
     * Bank's account list
     */
    private ArrayList<Account> accounts;

    /**
     * Create a new Bank object with empty lists of users and accounts
     * @param name name the name of the bank
     */
    public Bank(String name){

        this.name = name;
        this.accounts = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    /**
     *  Generate a new universally unique ID for a user.
     * @return the uuid
     */
    public String getNewUserUUID(){
        // inits
        String uuid;
        Random rng = new Random();
         int len = 6;
         boolean nonUnique;

         //continue looping until we get a unique ID
         do {

             // generate the number
             uuid = "";
             for (int i = 0; i < len; i++) {
                 uuid += ((Integer)rng.nextInt(10)).toString();
             }

             // check to make sure it's unique
             nonUnique = false;
             for(User u:this.users){
                 if (uuid.compareTo(u.getUuid()) == 0){
                     nonUnique = true;
                     break;
                 }
             }
         }while (nonUnique);

         return uuid;
    }

    /**
     * Generate a new universally unique ID for an account
     * @return the uuid
     */
    public String getNewAccountUUID(){
        // inits
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        //continue looping until we get a unique ID
        do {

            // generate the number
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            // check to make sure it's unique
            nonUnique = false;
            for(Account a:this.accounts){
                if (uuid.compareTo(a.getUuid()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while (nonUnique);

        return uuid;
    }

    /**
     * Add account
     * @param anAcct the account to add
     */
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    /**
     * Create a new user of the bank
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param pin the user's pin
     * @return the new User object
     */
    public User addUser(String firstName, String lastName, String pin){

        // create a new User objects and add it to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // creat ea savings account for the user
        Account newAccount = new Account("Saving",newUser, this);
        // add to holder and lists
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    /**
     * Get the User objects associated with a particular userID and pin, if they are valid
     * @param userId the UUID of the user
     * @param pin the pin of the user
     * @return the User object if the login is successful, or null, if it is not
     */
    public User userLogIn(String userId, String pin){
        // search through list of users
        for (User u:this.users){
            // check user ID is correct
            if(u.getUuid().compareTo(userId) == 0 && u.validatePin(pin)){
                return u;
            }
        }

        // if we haven't found the user or have an incorrect pin
        return null;
    }

    public String getName() {
        return name;
    }
}
