package uk.co.jacoblever;

import uk.co.jacoblever.domain.Account;

import java.math.BigDecimal;

public interface MoneyTransferer {
    boolean transferMoney(Account from, Account to, BigDecimal amount);
}
