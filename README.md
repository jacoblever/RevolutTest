# RevolutTest
Simple REST API to transfer money between accounts for the Revolut Java test

# Requirements
This API is built using the [Jersey framework](https://jersey.github.io/) with [Jackson](https://github.com/FasterXML/jackson) for JSON parsing.
The test suite uses JUnit for assertions and [mockito](http://site.mockito.org/) for mocking.
It was devolopeing using IntelliJ IDEA Community Edition on macOS with [Maven](https://maven.apache.org/) installed using [Homebrew](https://brew.sh/), although it should run anywhere Maven and Java 8 are installed.

# Running the API
To start the server run
```
mvn exec:java
```
from the root directory of the repository.

You can then call the API by making POST requests to `http://localhost:8080/revolut-test/tranfer/` with the following JSON body:
```
{
  "from-account-number": 1,
  "to-account-number": 2,
  "amount": "1.99"
}
```

For example, you can use curl to make a request as follows:
```
curl -X POST -H "Content-Type: application/json" -d '{"from-account-number":1,"to-account-number":2,"amount":"1.99"}' http://localhost:8080/revolut-test/transfer
```
# Limitations
* `transfer` is the only API endpoint at the moment. Obviously a real system would need the ability to create and manage the accounts as well. For testing perposses there are 3 sample accounts created on start up:

Account Number | Opening Balance
--- | ---
1 | £30
2 | £10
3 | £100

* There is also no recond of what transfers have been made, for a real banking system this would be very important and so maintaining an immutable ledger of transactions would be important.

# Tests
The tests can most easily be run from within IntelliJ.

# Lessons Learnt
* BigDecimal probably isn't the best type to use for the balance, I think using a int (or long) holding the smallest denomination of the currency would be best (eg pence).

