package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
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

        //if the @ is after the period
        if(email.lastIndexOf('@') > email.lastIndexOf('.')){
            return false;
        }
        else{
            //if suffix has less than 2 characters after the period  g@f.om
            if(email.lastIndexOf('.') > email.length() - 3){
                return false;
            }
        }

        return true;

        
    }
}