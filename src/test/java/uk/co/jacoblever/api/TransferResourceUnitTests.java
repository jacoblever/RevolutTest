package uk.co.jacoblever.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.jacoblever.domain.Account;
import uk.co.jacoblever.Accounts;
import uk.co.jacoblever.Main;
import uk.co.jacoblever.MoneyTransferer;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferResourceUnitTests {

    private HttpServer server;
    private WebTarget target;
    private Accounts mockAccounts = mock(Accounts.class);
    private MoneyTransferer mockMoneyTransferer = mock(MoneyTransferer.class);

    @Before
    public void setUp() throws Exception {
        server = Main.startServer(mockAccounts, mockMoneyTransferer);
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void postWithEmptyBodyGives400() {
        Response response = target.path("transfer")
                .request()
                .post(Entity.json(""));
        assertEquals(400, response.getStatus());
        assertEquals("{\"message\":\"You must supply a request body\"}", response.readEntity(String.class));
    }

    @Test
    public void testIfAmountDoesNotParseReturn400() {
        Response response = target.path("transfer")
                .request()
                .post(Entity.json("{\"from-account-number\":12345678,\"to-account-number\":87654321,\"amount\":\"17rtd\"}"));
        assertEquals(400, response.getStatus());
        assertEquals("{\"message\":\"Amount '17rtd' invalid\"}", response.readEntity(String.class));
    }

    @Test
    public void testIfAmountIsZeroReturn400() {
        Response response = target.path("transfer")
                .request()
                .post(Entity.json("{\"from-account-number\":12345678,\"to-account-number\":87654321,\"amount\":\"0\"}"));
        assertEquals(400, response.getStatus());
        assertEquals("{\"message\":\"Amount must be positive\"}", response.readEntity(String.class));
    }

    @Test
    public void testIfAmountIsNegativeReturn400() {
        Response response = target.path("transfer")
                .request()
                .post(Entity.json("{\"from-account-number\":12345678,\"to-account-number\":87654321,\"amount\":\"-1\"}"));
        assertEquals(400, response.getStatus());
        assertEquals("{\"message\":\"Amount must be positive\"}", response.readEntity(String.class));
    }

    @Test
    public void testIfAccountNumbersAreTheSameReturn400() {
        Response response = target.path("transfer")
                .request()
                .post(Entity.json("{\"from-account-number\":12345678,\"to-account-number\":12345678,\"amount\":\"1\"}"));
        assertEquals(400, response.getStatus());
        assertEquals("{\"message\":\"Cannot transfer to and from the same account\"}", response.readEntity(String.class));
    }

    @Test
    public void testIfFromAccountDoesNotExistReturn404() {
        Response response = target.path("transfer")
                .request()
                .post(Entity.json("{\"from-account-number\":12345678,\"to-account-number\":87654321,\"amount\":\"101\"}"));
        assertEquals(404, response.getStatus());
        assertEquals("{\"message\":\"Account number 12345678 not recognised\"}", response.readEntity(String.class));
    }

    @Test
    public void testIfToAccountDoesNotExistReturn404() {
        when(mockAccounts.getAccount(12345678))
                .thenReturn(new Account(12345678));
        Response response = target.path("transfer")
                .request()
                .post(Entity.json("{\"from-account-number\":12345678,\"to-account-number\":87654321,\"amount\":\"101\"}"));
        assertEquals(404, response.getStatus());
        assertEquals("{\"message\":\"Account number 87654321 not recognised\"}", response.readEntity(String.class));
    }

    @Test
    public void testIfSuccessfulTransferReturn200() {
        when(mockAccounts.getAccount(12345678))
                .thenReturn(new Account(12345678));
        when(mockAccounts.getAccount(87654321))
                .thenReturn(new Account(87654321));
        when(mockMoneyTransferer.transferMoney(any(Account.class), any(Account.class), any(BigDecimal.class)))
                .thenReturn(true);
        Response response = target.path("transfer")
                .request()
                .post(Entity.json("{\"from-account-number\":12345678,\"to-account-number\":87654321,\"amount\":\"5.99\"}"));
        assertEquals(200, response.getStatus());
        assertEquals("{\"message\":\"Money successfully transfered\"}", response.readEntity(String.class));
    }

    @Test
    public void testIfUnsuccessfulTransferDueToFundsReturn402() {
        when(mockAccounts.getAccount(12345678))
                .thenReturn(new Account(12345678));
        when(mockAccounts.getAccount(87654321))
                .thenReturn(new Account(87654321));
        when(mockMoneyTransferer.transferMoney(any(Account.class), any(Account.class), any(BigDecimal.class)))
                .thenReturn(false);
        Response response = target.path("transfer")
                .request()
                .post(Entity.json("{\"from-account-number\":12345678,\"to-account-number\":87654321,\"amount\":\"1\"}"));
        assertEquals(402, response.getStatus());
        assertEquals("{\"message\":\"Account number 12345678 does not have the required funds\"}", response.readEntity(String.class));
    }
}
