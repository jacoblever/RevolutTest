package uk.co.jacoblever;

import java.math.BigDecimal;

public interface MoneyTransferer {
    boolean transferMoney(Account from, Account to, BigDecimal amount);
}
