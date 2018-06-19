package uk.co.jacoblever.in.memory.implementations;

class DuplicateAccountException extends RuntimeException {
    DuplicateAccountException(int accountNumber) {
        super("Cannot have two accounts with account number " + accountNumber);
    }
}
