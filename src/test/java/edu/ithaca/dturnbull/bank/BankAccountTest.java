package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);

        //minimum getBalance test
        bankAccount.withdraw(0);
        assertEquals(200, bankAccount.getBalance(), 0.001);

        //maximum getBalance test
        bankAccount.withdraw(200);
        assertEquals(0, bankAccount.getBalance(), 0.001);

        //resetting the account
        bankAccount = new BankAccount("a@b.com", 200);

        //border getBalance test
        bankAccount.withdraw(1);
        assertEquals(199, bankAccount.getBalance(), 0.001);

        //border getBalance test
        bankAccount.withdraw(198);
        assertEquals(1, bankAccount.getBalance(), 0.001);

        //resetting the account
        //bankAccount.withdraw(-199);

        //border getBalance test
        //bankAccount.withdraw(-1);
        //assertEquals(201, bankAccount.getBalance(), 0.001);
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        BankAccount bankAccount1 = new BankAccount("a@b.com", 500);
        BankAccount bankAccount2 = new BankAccount("b@a.com", 0);

        //Valid non-negative amount and valid sender and receiver
        BankAccount.transfer(bankAccount1, bankAccount2, 250);
        assertEquals(250, bankAccount1.getBalance(), .001);
        assertEquals(250, bankAccount2.getBalance(), .001);

        BankAccount.transfer(bankAccount1, bankAccount2, 0);
        assertEquals(250, bankAccount1.getBalance(), .001); //Border
        assertEquals(250, bankAccount2.getBalance(), .001); //Border

        BankAccount.transfer(bankAccount1, bankAccount2, 250);
        assertEquals(0, bankAccount1.getBalance(), .001); //Border
        assertEquals(500, bankAccount2.getBalance(), .001); //Border

        BankAccount.transfer(bankAccount2, bankAccount1, 499.99);
        assertEquals(499.99, bankAccount1.getBalance(), .001); //Border
        assertEquals(0.01, bankAccount2.getBalance(), .001); //Border

        

        //Sender doesn't have enough
        assertThrows(InsufficientFundsException.class,() -> BankAccount.transfer(bankAccount1, bankAccount2, 1000));
        assertThrows(InsufficientFundsException.class,() -> BankAccount.transfer(bankAccount1, bankAccount2, 500));//Border

        //Receiver and Sender are the same
        assertThrows(IllegalArgumentException.class,() -> BankAccount.transfer(bankAccount1, bankAccount1, 0));

        //Invalid amount
        //Negative amount
        assertThrows(IllegalArgumentException.class,() -> BankAccount.transfer(bankAccount1, bankAccount2, -200));
        assertThrows(IllegalArgumentException.class,() -> BankAccount.transfer(bankAccount1, bankAccount2, -0.01)); //border

        //More than 2 decimal places
        assertThrows(IllegalArgumentException.class,() -> BankAccount.transfer(bankAccount1, bankAccount2, 212.34876));
        assertThrows(IllegalArgumentException.class,() -> BankAccount.transfer(bankAccount1, bankAccount1, 3.141)); //border


    }

    @Test
    void isAmountValidTest(){
        //Positive valid amount
        assertTrue(BankAccount.isAmountValid(100));
        assertTrue(BankAccount.isAmountValid(0.01)); //Border
        assertTrue(BankAccount.isAmountValid(10.1)); //Only 1 Decimal place


        //Negative amount
        assertFalse(BankAccount.isAmountValid(-100));
        assertFalse(BankAccount.isAmountValid(-0.01)); //Border

        //Zero amount
        assertTrue(BankAccount.isAmountValid(0));
        assertTrue(BankAccount.isAmountValid(0.00));

        //More than two decimal places
        assertFalse(BankAccount.isAmountValid(10.012)); //border
        assertFalse(BankAccount.isAmountValid(10.002453245));
        assertTrue(BankAccount.isAmountValid(10.010)); 
    }

    @Test
    void depositTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 0);

        //valid positive amount
        bankAccount.deposit(100); 
        assertEquals(100, bankAccount.getBalance(), .001);

        bankAccount.deposit(0.01);
        assertEquals(100.01, bankAccount.getBalance(), .001); //border

        //Negative amount
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-0.01)); //border

        //Amount with more than 2 decimals
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(100.23467435));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(100.234));//border
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);

        //Testing the maximum you can withdraw from the bank
        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance(), 0.001);

        //Testing the minimum you can withdraw
        bankAccount.withdraw(0);
        assertEquals(0, bankAccount.getBalance(), 0.001);

        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        //Withdraw negative amount        
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-300));

        //withdraw amount with more than 2 decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(300.739));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string
        assertFalse(BankAccount.isEmailValid("@.com"));     //no prefix incomplete suffix -> Boundary value: At least one character in prefix
        assertFalse( BankAccount.isEmailValid("ab@c@d@.com"));  //mulitple @ symbols -> Equivalence: invalid characters in prefix
        assertFalse( BankAccount.isEmailValid(".com@"));    //incorrectly formatted suffix -> Equivalence: invalid characters in prefix
        assertFalse(BankAccount.isEmailValid("abc@gmail.v")); // valid prefix invalid suffix
        assertTrue(BankAccount.isEmailValid("ab@gmail.com")); //prefix with only two letters
        assertTrue(BankAccount.isEmailValid("wizard.ofoz@ithaca.edu")); //prefix with period
        //I think this is a valid email -> assertFalse( BankAccount.isEmailValid("chris@to.pher.com"));  //additional period in suffix
        assertFalse( BankAccount.isEmailValid("rumplestiltskin.com")); //no @ symbol
        assertFalse( BankAccount.isEmailValid("pinocchio@gmail.com$")); //invalid suffix
        assertFalse( BankAccount.isEmailValid("captain.@hook.com")); //period with no character after
        assertFalse( BankAccount.isEmailValid("jiminy_@cricket.com"));//underscore with no character after
        //Potential missing tests
        //Valid prefix, invalid suffix
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        //invalid starting amounts
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.732));
    }

}