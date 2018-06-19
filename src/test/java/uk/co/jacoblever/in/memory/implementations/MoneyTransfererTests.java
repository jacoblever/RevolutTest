package uk.co.jacoblever.in.memory.implementations;

import org.junit.Before;
import org.junit.Test;
import uk.co.jacoblever.domain.Account;
import uk.co.jacoblever.MoneyTransferer;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class MoneyTransfererTests {

    private Account from;
    private Account to;
    private MoneyTransferer transferer;

    @Before
    public void setup(){
        from = new Account(1, "10");
        to = new Account(2, "20");
        transferer = new InMemoryMoneyTransferer();
    }

    @Test
    public void CanTransferMoney() {
        boolean result = transferer.transferMoney(from, to, new BigDecimal("5"));
        assertEquals(new BigDecimal("5"), from.getBalance());
        assertEquals(new BigDecimal("25"), to.getBalance());
        assertEquals(true, result);
    }

    @Test
    public void TransferNotAllowedIfNotEnoughFunds() {
        boolean result = transferer.transferMoney(from, to, new BigDecimal("15"));
        assertEquals(new BigDecimal("10"), from.getBalance());
        assertEquals(new BigDecimal("20"), to.getBalance());
        assertEquals(false, result);
    }

    @Test(expected = TransferAmountMustBePositiveException.class)
    public void TransferNotAllowedIfAmountIsNegative() {
        BigDecimal amount = new BigDecimal("15").negate();
        transferer.transferMoney(from, to, amount);
    }

    @Test(expected = TransferAmountMustBePositiveException.class)
    public void TransferNotAllowedIfAmountIsZero() {
        BigDecimal amount = BigDecimal.ZERO;
        transferer.transferMoney(from, to, amount);
    }

    @Test(expected = TransferAccountsMustBeDifferentException.class)
    public void TransferNotAllowedBetweenSameAccounts() {
        transferer.transferMoney(from, from, BigDecimal.ONE);
    }
}
