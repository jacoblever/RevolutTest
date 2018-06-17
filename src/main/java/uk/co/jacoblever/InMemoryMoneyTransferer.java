package uk.co.jacoblever;

import java.math.BigDecimal;

public class InMemoryMoneyTransferer implements MoneyTransferer {

    public InMemoryMoneyTransferer() {
    }

    @Override
    public boolean transferMoney(Account from, Account to, BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new TransferAmountMustBePositiveException(amount);
        }

        boolean withdrawSuccessful = from.tryChangeBalance(amount.negate());
        if(!withdrawSuccessful)
        {
            return false;
        }

        to.tryChangeBalance(amount);
        return true;
    }
}
