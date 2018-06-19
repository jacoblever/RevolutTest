package uk.co.jacoblever.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {

    @JsonProperty("from-account-number")
    private int fromAccountNumber;

    @JsonProperty("to-account-number")
    private int toAccountNumber;

    @JsonProperty("amount")
    private String amount;

    public int getFromAccountNumber() {
        return fromAccountNumber;
    }

    public int getToAccountNumber() {
        return toAccountNumber;
    }

    public String getAmount() {
        return amount;
    }
}
