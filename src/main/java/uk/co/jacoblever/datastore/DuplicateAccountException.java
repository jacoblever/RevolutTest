package uk.co.jacoblever.datastore;

class DuplicateAccountException extends RuntimeException {
    DuplicateAccountException(int accountNumber) {
        super("Cannot have two accounts with account number " + accountNumber);
    }
}
