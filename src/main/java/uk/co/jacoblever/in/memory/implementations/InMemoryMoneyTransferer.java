package uk.co.jacoblever.in.memory.implementations;

import uk.co.jacoblever.domain.Account;
import uk.co.jacoblever.MoneyTransferer;

import java.math.BigDecimal;

/**
 * Because the Accounts are stored in memory there is no need to 'save' the changes
 * made to them. Therefore in the real world this would also include persisting the
 * changes
 */
public class InMemoryMoneyTransferer implements MoneyTransferer {
    @Override
    public boolean transferMoney(Account from, Account to, BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new TransferAmountMustBePositiveException(amount);
        }

        if(from.getAccountNumber() == to.getAccountNumber()) {
            throw new TransferAccountsMustBeDifferentException(from.getAccountNumber());
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
