package uk.co.jacoblever.api;

import uk.co.jacoblever.domain.Account;
import uk.co.jacoblever.Accounts;
import uk.co.jacoblever.MoneyTransferer;
import uk.co.jacoblever.api.models.TransferRequest;
import uk.co.jacoblever.api.models.TransferResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("transfer")
public class TransferResource {

    private Accounts accounts;
    private MoneyTransferer moneyTransferer;

    public TransferResource(Accounts accounts, MoneyTransferer moneyTransferer) {
        this.accounts = accounts;
        this.moneyTransferer = moneyTransferer;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt(TransferRequest transfer) {
        if (transfer == null) {
            return respond(400, "You must supply a request body");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(transfer.getAmount());
        } catch (NumberFormatException e) {
            return respond(400, String.format("Amount '%s' invalid", transfer.getAmount()));
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return respond(400, "Amount must be positive");
        }

        if (transfer.getFromAccountNumber() == transfer.getToAccountNumber()) {
            return respond(400, "Cannot transfer to and from the same account");
        }

        Account fromAccount = accounts.getAccount(transfer.getFromAccountNumber());
        if (fromAccount == null) {
            return respond(404, String.format("Account number %d not recognised", transfer.getFromAccountNumber()));
        }

        Account toAccount = accounts.getAccount(transfer.getToAccountNumber());
        if (toAccount == null) {
            return respond(404, String.format("Account number %d not recognised", transfer.getToAccountNumber()));
        }

        return transferAmount(fromAccount, toAccount, amount);
    }

    private Response transferAmount(Account fromAccount, Account toAccount, BigDecimal amount) {
        boolean transferSucceeded = moneyTransferer.transferMoney(fromAccount, toAccount, amount);
        if (!transferSucceeded) {
            return respond(
                    402,
                    String.format(
                            "Account number %d does not have the required funds",
                            fromAccount.getAccountNumber()));
        }
        return respond(200, "Money successfully transfered");
    }

    private Response respond(int status, String message) {
        TransferResponse response = new TransferResponse(message);
        return Response.status(status)
                .entity(response)
                .build();
    }
}
