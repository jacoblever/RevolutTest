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
    public void Can_increase_balance() {
        Account account = new Account(1, "9.89");
        boolean result = account.tryChangeBalance(BigDecimal.ONE);
        assertEquals(new BigDecimal("10.89"), account.getBalance());
        assertEquals(true, result);
    }

    @Test
    public void Can_decrease_balance_when_funds_available() {
        Account account = new Account(1, "9.89");
        boolean result = account.tryChangeBalance(BigDecimal.ONE.negate());
        assertEquals(new BigDecimal("8.89"), account.getBalance());
        assertEquals(true, result);
    }

    @Test
    public void Can_NOT_decrease_balance_when_not_enough_funds_available() {
        Account account = new Account(1, "0.99");
        boolean result = account.tryChangeBalance(BigDecimal.ONE.negate());
        assertEquals(new BigDecimal("0.99"), account.getBalance());
        assertEquals(false, result);
    }
}
