package com.company;

import java.lang.annotation.Target;
import java.util.Date;

public class Transaction {
    /**
     * The amount of this transaction.
     */
    private  double amount;

    /**
     * The time and date of this transaction.
     */
    private Date timestamp;

    /**
     * A memo for this transaction.
     */
    private String memo;

    /**
     * The account in which transaction was performed.
     */
    private Account inAccount;

    public Transaction(double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account inAccount){
        // call the two-arg constructor first
        this(amount, inAccount);

        // set the memo
        this.memo = memo;
    }

    /**
     * Get the amount of the transaction
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Get a string summarizing the transaction
     * @return the summary string
     */
    public String getSummaryLine(){
        if (this.amount >= 0){
            return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        }
        else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), this.amount, this.memo);
        }
    }
}
