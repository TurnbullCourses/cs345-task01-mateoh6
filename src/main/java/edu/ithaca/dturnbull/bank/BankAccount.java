package edu.ithaca.dturnbull.bank;

import javax.sound.sampled.SourceDataLine;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }

        if(isAmountValid(startingBalance)){
            this.balance = startingBalance;
        }
        else{
            throw new IllegalArgumentException("Invalid starting balance amount");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * 
     * @param amount
     */
    public static boolean isAmountValid(double amount){
        //negative amounts
        if(amount < 0){
            return false;
        }

        //more than two decimals
        int digits;
        double testAmount = amount;

        //move left 2 decimals
        testAmount *= 100;

        //cut off the rest of the digits
        digits = (int) testAmount;
        testAmount = digits;
        testAmount /= 100;

        if(testAmount != amount){
            return false;
        }

        return true;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsuffientFundsException
     * @throws IllegalArgumentException
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(isAmountValid(amount)){
            if (amount <= balance){
                balance -= amount;
            }
            else {
                throw new InsufficientFundsException("Not enough money");
            }
        }
        else{
            throw new IllegalArgumentException("Invalid Withdraw Amount");
        }        
    }

    /**
     * Deposits a valid amount in the account and adds to balance
     * @param amount
     * @throws IllegalArgumentException
     */
    public void deposit(double amount){
        if(isAmountValid(amount)){
            balance += amount;
        }
        else{
            throw new IllegalArgumentException("Invalid Deposit Amount");
        }
    }


    /**
     * Transfers amount from sender to receiver
     * @param sender
     * @param receiver
     * @param amount
     * @throws InsufficientFundsException
     * @throws IllegalArgumentException
     */
    public static void transfer(BankAccount sender, BankAccount receiver, double amount){
        
    }

    public static boolean isEmailValid(String email){
        
        if(email.length() <= 0)
            return false;

        //If first character is not a letter or number
        if(email.charAt(0) < 'A' || email.charAt(0) > 'z'){
            return false;
        }
        else if(email.charAt(0) > 'Z' && email.charAt(0) < 'a'){
            return false;
        }
        else if(email.charAt(0) > '9' && email.charAt(0) < '0'){
            return false;
        }
        
        
        //if it does not contain an @ or has no prefix
        if (email.indexOf('@') == -1 || email.indexOf('@') == 0){
            return false;
        }
        else {
            //if there is more than 1 @
            int count = 0;
            for(int i = 0; i<email.length(); i++){
                if(email.charAt(i) == '@'){
                    count++;
                }
            }

            if(count > 1)
                return false;
        }

        int lastPeriodIndex = email.lastIndexOf('.');
        //if the @ is after the period
        if(email.lastIndexOf('@') > lastPeriodIndex){
            return false;
        }
        else{
            //if suffix has less than 2 characters after the period  g@f.om
            if(lastPeriodIndex > email.length() - 3){
                return false;
            }


            //if a period isn't followed by a letter or number
            for(int i = 0; i < email.length(); i++){
                if(email.charAt(i) == '.'){
                    //period not followed by letter
                    if(email.charAt(i+1) > 122 || email.charAt(i+1) < 65){
                        return false;
                    }
                    else if(email.charAt(i+1) > 90 && email.charAt(i+1) < 97){
                            return false;
                    }
                    else if(email.charAt(i+1) > '9' && email.charAt(i+1) < '0'){
                        return false;
                    }
                }
            }

            //if a underscore isn't followed by a letter or number
            for(int i = 0; i < email.length(); i++){
                if(email.charAt(i) == '_'){
                    //period not followed by letter
                    if(email.charAt(i+1) > 122 || email.charAt(i+1) < 65){
                        return false;
                    }
                    else if(email.charAt(i+1) > 90 && email.charAt(i+1) < 97){
                            return false;
                    }
                    else if(email.charAt(i+1) > '9' && email.charAt(i+1) < '0'){
                        return false;
                    }
                }
            }

            //if a dash isn't followed by a letter or number
            for(int i = 0; i < email.length(); i++){
                if(email.charAt(i) == '-'){
                    //period not followed by letter
                    if(email.charAt(i+1) > 122 || email.charAt(i+1) < 65){
                        return false;
                    }
                    else if(email.charAt(i+1) > 90 && email.charAt(i+1) < 97){
                            return false;
                    }
                    else if(email.charAt(i+1) > '9' && email.charAt(i+1) < '0'){
                        return false;
                    }
                }
            }
        }

        //if any domain character isn't a lower case letter or period
        for(int i = email.indexOf('@') + 1; i < email.length(); i++){
            if(email.charAt(i) < 97 || email.charAt(i) > 122){
                if(email.charAt(i) != '.'){
                    return false;
                }             
            }
        }

        return true;

        
    }
}