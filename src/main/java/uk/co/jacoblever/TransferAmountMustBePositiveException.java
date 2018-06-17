package uk.co.jacoblever;

import java.math.BigDecimal;

class TransferAmountMustBePositiveException extends RuntimeException {
    public TransferAmountMustBePositiveException(BigDecimal amount) {
        super("Cannot transfer a non-positive amount of money: " + amount);
    }
}
