package uk.co.jacoblever;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import uk.co.jacoblever.api.TransferResource;
import uk.co.jacoblever.domain.Account;
import uk.co.jacoblever.in.memory.implementations.InMemoryAccountsDatastore;
import uk.co.jacoblever.in.memory.implementations.InMemoryMoneyTransferer;

import java.io.IOException;
import java.net.URI;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/revolut-test/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer(Accounts accounts, MoneyTransferer moneyTransferer) {
        final ResourceConfig rc = new ResourceConfig();

        // todo: Use proper dependency injection framework here
        // so we can use the more general to catch all resources
        //      .packages("uk.co.jacoblever")
        rc.register(new TransferResource(accounts, moneyTransferer));

        rc.register(JacksonFeature.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer(
                new InMemoryAccountsDatastore(getSampleAccounts()),
                new InMemoryMoneyTransferer()
        );
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }

    /**
     * These are some sample accounts for testing the system from the command line
     * @return Sample accounts
     */
    private static Account[] getSampleAccounts() {
        return new Account[]{
                new Account(1, "30"),
                new Account(2, "10"),
                new Account(3, "100")
        };
    }
}
