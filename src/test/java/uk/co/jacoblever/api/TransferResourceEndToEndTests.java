package uk.co.jacoblever.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.jacoblever.Main;
import uk.co.jacoblever.domain.Account;
import uk.co.jacoblever.in.memory.implementations.InMemoryAccountsDatastore;
import uk.co.jacoblever.in.memory.implementations.InMemoryMoneyTransferer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TransferResourceEndToEndTests {
    private HttpServer server;
    private WebTarget target;
    private Account account1;
    private Account account2;

    @Before
    public void setUp() throws Exception {
        account1 = new Account(12345678, "20");
        account2 = new Account(87654321, "10");
        server = Main.startServer(
                new InMemoryAccountsDatastore(
                        new Account[]{
                                account1,
                                account2
                        }
                ),
                new InMemoryMoneyTransferer());
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testCanSuccessfullyTransferMoney() {
        Response response = target.path("transfer")
                .request()
                .post(Entity.json("{\"from-account-number\":12345678,\"to-account-number\":87654321,\"amount\":\"5.99\"}"));
        assertEquals(200, response.getStatus());

        assertEquals(new BigDecimal("14.01"), account1.getBalance());
        assertEquals(new BigDecimal("15.99"), account2.getBalance());
    }

    @Test
    public void testDoesNotTransferMoneyIfNoFunds() {
        Response response = target.path("transfer")
                .request()
                .post(Entity.json("{\"from-account-number\":12345678,\"to-account-number\":87654321,\"amount\":\"25\"}"));
        assertEquals(402, response.getStatus());

        assertEquals(new BigDecimal("20"), account1.getBalance());
        assertEquals(new BigDecimal("10"), account2.getBalance());
    }
}
