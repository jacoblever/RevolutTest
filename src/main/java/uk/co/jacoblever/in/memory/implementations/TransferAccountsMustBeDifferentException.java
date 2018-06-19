package uk.co.jacoblever.in.memory.implementations;

class TransferAccountsMustBeDifferentException extends RuntimeException {
    public TransferAccountsMustBeDifferentException(int accountNumber) {
        super(String.format("Cannot transfer to and from the same account (number: %d)", accountNumber));
    }
}
