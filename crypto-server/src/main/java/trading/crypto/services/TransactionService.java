package trading.crypto.services;

import trading.crypto.data.models.Transaction;

public interface TransactionService {
    int createTransaction(Transaction transaction);
}
