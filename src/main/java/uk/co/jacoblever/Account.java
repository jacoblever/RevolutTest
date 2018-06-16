package uk.co.jacoblever;

import java.math.BigDecimal;

public class Account {
    private int accountNumber;
    private BigDecimal balance;

    private Account(int accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account(int accountNumber, String openingBalance) {
        this(accountNumber, new BigDecimal(openingBalance));
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
