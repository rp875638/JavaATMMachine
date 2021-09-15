package com.company;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        // init Scanner
        Scanner scanner = new Scanner(System.in);

        // init Bank
        Bank theBank = new Bank("Bank of Baroda");

        // add a user, which also creates a saving account
        User aUser = theBank.addUser("Ram", "Yadav", "1234");

        // add a checking account for our user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while(true){
            // stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, scanner);

            // stay in main menu until user quits
            ATM.printUserMenu(curUser, scanner);
            scanner.nextLine();
        }
    }

    /**
     *  Print the ATM's menu
     * @param theBank the Bank object account to use
     * @param scanner the Scanner object to use user input
     * @return the authenticated User objects
     */
     public static User mainMenuPrompt(Bank theBank, Scanner scanner){
        // inits
        String userID;
        String pin;
        User authUser;

        //prompt the user for user  ID/pin combo until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n",theBank.getName());
            System.out.print("Enter user ID: ");
            userID = scanner.nextLine();
            System.out.print("Enter pin: ");
            pin = scanner.nextLine();

            // try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogIn(userID,pin);
            if (authUser == null){
                System.out.println("Incorrect user ID/pin combination. "+"Please try again");
            }
        }while (authUser == null);

        return authUser;
    }

    /**
     * Print the User account menu
     * @param theUser the User object to get logged-in user
     * @param scanner the Scanner object to use user input
     */
     public static void printUserMenu(User theUser, Scanner scanner){
        // print a summary of the user's accounts
        theUser.printAccountSummery();

        //init
        int choice;

        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println("1) Show account transaction history");
            System.out.println("2) Withdrawal");
            System.out.println("3) Deposit");
            System.out.println("4) Transfer");
            System.out.println("5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            if(choice<1 || choice>5)
                System.out.println("Invalid choice, Please choose 1-5");
        }while (choice<1 || choice>5);

        // process the choice
        switch (choice){
            case 1:
                ATM.showTransactionHistory(theUser, scanner);
                break;
            case 2:
                ATM.withdrawalFunds(theUser, scanner);
                break;
            case 3:
                ATM.depositFunds(theUser, scanner);
                break;
            case 4:
                ATM.transferFunds(theUser, scanner);
                break;
        }

        // redisplay this menu unless the user wants to quit
        if (choice != 5){
            ATM.printUserMenu(theUser, scanner);
        }
    }

    /**
     * Show the transaction history for an account
     * @param theUser the logged-in User object
     * @param scanner the Scanner object used for user input
     */
    public static void showTransactionHistory(User theUser, Scanner scanner){
         int theAcct;

         // get account whose transactions history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"+ "whose transaction you want to see: ", theUser.numAccounts());
            theAcct = scanner.nextInt()-1;
            if(theAcct < 0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid account Please try again. ");
            }
        }while (theAcct < 0 || theAcct >= theUser.numAccounts());

        //print the transaction history
        theUser.printAccountTransactionHistory(theAcct);
    }

    /**
     *  Process transferring funds one account to another
     * @param theUser the logged-in User object
     * @param scanner the Scanner object used for user input
     */
    public static void transferFunds(User theUser, Scanner scanner){
        // inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"+ "to transfer from: ", theUser.numAccounts());
            fromAcct = scanner.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again");
            }
        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

        acctBal =  theUser.getAccountBalance(fromAcct);

        //get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"+ "to transfer to: ", theUser.numAccounts());
            toAcct = scanner.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again");
            }
        }while (toAcct < 0 || toAcct >= theUser.numAccounts());

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer ( max $%.02f): $", acctBal);
            amount = scanner.nextInt();
            if (amount < 0){
                System.out.println("Amount must be greater than zero");
            }
            else if (amount > acctBal){
                System.out.printf("Amount must not be greater than balance of $%.02f.\n",acctBal);
            }
        }while (amount < 0 || amount > acctBal);

        // finally do the transfer
        theUser.addAccountTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAccountUuid(toAcct)));
        theUser.addAccountTransaction(toAcct, amount, String.format("Transfer from account %s", theUser.getAccountUuid(fromAcct)));
    }

    /**
     * Process a fund withdrawal from an account
     * @param theUser the logged-in User object
     * @param scanner the Scanner object user for user input
     */
    public  static void withdrawalFunds(User theUser, Scanner scanner){
        // inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"+ "to transfer from: ", theUser.numAccounts());
            fromAcct = scanner.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again");
            }
        }while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

        acctBal =  theUser.getAccountBalance(fromAcct);
        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer ( max $%.02f): $", acctBal);
            amount = scanner.nextInt();
            if (amount < 0){
                System.out.println("Amount must be greater than zero");
            }
            else if (amount > acctBal){
                System.out.printf("Amount must not be greater than balance of $%.02f.\n",acctBal);
            }
        }while (amount < 0 || amount > acctBal);

        // gobble up rest of previous input
        scanner.nextLine();

        // get a memo
        System.out.print("Enter a memo: ");
        memo = scanner.nextLine();

        // do withdrawal
        theUser.addAccountTransaction(fromAcct, -1*amount, memo);

    }

    /**
     * Process a fund deposit to an account
     * @param theUser the Logged-in User object
     * @param scanner the Scanner object used for input
     */
    public static void depositFunds(User theUser, Scanner scanner){
        // inits
        int tocAcct;
        double amount;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"+ "to deposit: ", theUser.numAccounts());
            tocAcct = scanner.nextInt()-1;
            if (tocAcct < 0 || tocAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again");
            }
        }while (tocAcct < 0 || tocAcct >= theUser.numAccounts());

        // get the amount to transfer
        do {
            System.out.print("Enter the amount to deposit: $");
            amount = scanner.nextInt();
            if (amount < 0){
                System.out.println("Amount must be greater than zero");
            }
        }while (amount < 0);

        // gobble up rest of previous input
        scanner.nextLine();

        // get a memo
        System.out.print("Enter a memo: ");
        memo = scanner.nextLine();

        // do withdrawal
        theUser.addAccountTransaction(tocAcct, amount, memo);

    }
}
