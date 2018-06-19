package uk.co.jacoblever;

import uk.co.jacoblever.domain.Account;

/**
 * The contract for out datastore
 * Obviously in a real application there would need to be create/update/delete operations as well
 */
public interface Accounts {
    /**
     * Returns the account with matching account number or null if no such account exists
     * @param accountNumber Account number to retrieve
     * @return The Account or null
     */
    Account getAccount(int accountNumber);
}
