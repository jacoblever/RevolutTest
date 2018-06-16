package uk.co.jacoblever;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccountTests {

    @Test
    public void Can_get_account_number() {
        Account account = new Account(12345678, "0");
        assertEquals(12345678, account.getAccountNumber());
    }

    @Test
    public void An_account_with_zero_balance_reports_a_balance_of_zero() {
        Account account = new Account(1, "0");
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    public void An_account_with_integer_balance_reports_the_correct_balance() {
        Account account = new Account(1, "9");
        assertEquals(new BigDecimal(9), account.getBalance());
    }

    @Test
    public void An_account_with_a_decimal_balance_reports_the_correct_balance() {
        Account account = new Account(1, "9.89");
        assertEquals(new BigDecimal("9.89"), account.getBalance());
    }

    @Test
    public void Can_change_balance() {
        Account account = new Account(1, "2.5");
        account.setBalance(new BigDecimal("1.99"));
        assertEquals(new BigDecimal("1.99"), account.getBalance());
    }
}
