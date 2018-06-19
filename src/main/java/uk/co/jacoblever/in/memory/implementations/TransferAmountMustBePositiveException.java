package uk.co.jacoblever.in.memory.implementations;

import java.math.BigDecimal;

class TransferAmountMustBePositiveException extends RuntimeException {
    TransferAmountMustBePositiveException(BigDecimal amount) {
        super("Cannot transfer a non-positive amount of money: " + amount);
    }
}
