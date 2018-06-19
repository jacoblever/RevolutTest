package uk.co.jacoblever;

import uk.co.jacoblever.domain.Account;

import java.math.BigDecimal;

/**
 * Contract for being able to transfer money from one account to another
 */
public interface MoneyTransferer {
    /**
     * Transfer money from on account to another. Returns false if not enough funds to transfer.
     * @param from Account to transfer money from
     * @param to Account to transfer money to
     * @param amount Amount to transfer as a BigDecimal
     * @return true if the transfer was successful, false if 'from' does not have enough funds
     */
    boolean transferMoney(Account from, Account to, BigDecimal amount);
}
