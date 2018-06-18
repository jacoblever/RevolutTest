package uk.co.jacoblever.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferResponse {

    @JsonProperty("message")
    private String message;

    public TransferResponse(String message) {
        this.message = message;
    }
}
