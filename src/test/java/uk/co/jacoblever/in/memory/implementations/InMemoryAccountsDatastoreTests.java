package uk.co.jacoblever.in.memory.implementations;

import org.junit.Test;
import uk.co.jacoblever.domain.Account;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class InMemoryAccountsDatastoreTests {
    @Test
    public void ReturnsNullWhenNoAccounts() {
        InMemoryAccountsDatastore accounts = new InMemoryAccountsDatastore(new Account[]{});
        assertNull(accounts.getAccount(12345678));
    }

    @Test
    public void CanGetAccount() {
        Account account = new Account(12345678, "10");
        InMemoryAccountsDatastore accounts = new InMemoryAccountsDatastore(new Account[]{account});

        assertSame(account, accounts.getAccount(12345678));
    }

    @Test
    public void CanGetAccountWhenSeveralToChooseFrom() {
        Account account = new Account(12345678, "10");
        InMemoryAccountsDatastore accounts = new InMemoryAccountsDatastore(new Account[]{
                new Account(87654321, "11"),
                new Account(1234, "12.30"),
                account
        });

        assertSame(account, accounts.getAccount(12345678));
    }

    @Test
    public void ReturnsNullIfNoMatches() {
        InMemoryAccountsDatastore accounts = new InMemoryAccountsDatastore(new Account[]{
                new Account(87654321, "11"),
                new Account(1234, "12.30")
        });

        assertNull(accounts.getAccount(12345678));
    }

    @Test(expected = DuplicateAccountException.class)
    public void DoesNotAllowTwoAccountsWithTheSameNumber(){
        new InMemoryAccountsDatastore(new Account[]{
                new Account(12345678, "10"),
                new Account(12345678, "11")
        });
    }
}
