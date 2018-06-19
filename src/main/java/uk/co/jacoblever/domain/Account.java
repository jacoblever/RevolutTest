package uk.co.jacoblever.domain;

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

    public Account(int accountNumber) {
        this(accountNumber, BigDecimal.ZERO);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public boolean tryChangeBalance(BigDecimal amount) {
        BigDecimal newBalance = balance.add(amount);
        if(newBalance.compareTo(BigDecimal.ZERO) < 0){
            return false;
        }
        balance = newBalance;
        return true;
    }
}
