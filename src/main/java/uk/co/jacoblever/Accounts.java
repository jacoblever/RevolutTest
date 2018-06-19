package uk.co.jacoblever;

import uk.co.jacoblever.domain.Account;

/**
 * The contract for out datastore
 */
public interface Accounts {
    /**
     * Returns the account with matching account number or null if no such account exists
     * @param accountNumber
     * @return The Account or null
     */
    Account getAccount(int accountNumber);
}
