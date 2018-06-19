package uk.co.jacoblever.in.memory.implementations;

import uk.co.jacoblever.domain.Account;
import uk.co.jacoblever.Accounts;

import java.util.HashMap;

/**
 * This is a simple in memory implementation of our Accounts datastore
 */
public class InMemoryAccountsDatastore implements Accounts {

    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public InMemoryAccountsDatastore(Account[] accounts) {
        for (Account account : accounts) {
            if(this.accounts.containsKey(account.getAccountNumber())){
                throw new DuplicateAccountException(account.getAccountNumber());
            }
            this.accounts.put(account.getAccountNumber(), account);
        }
    }

    @Override
    public Account getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }
}
