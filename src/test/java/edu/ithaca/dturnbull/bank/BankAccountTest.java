package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
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
        assertFalse( BankAccount.isEmailValid("chris@to.pher.com"));  //additional period in suffix
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
    }

}