package uk.co.jacoblever.datastore;

import uk.co.jacoblever.Account;
import uk.co.jacoblever.Accounts;

import java.util.HashMap;

/**
 * This is a simple in memory implementation of our Accounts datastore
 */
public class InMemoryAccountsDatastore implements Accounts {

    private final HashMap<Integer, Account> accounts = new HashMap<>();

    InMemoryAccountsDatastore(Account[] accounts) {
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
