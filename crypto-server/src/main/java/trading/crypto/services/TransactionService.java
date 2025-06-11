package trading.crypto.services;

import trading.crypto.data.models.Transaction;
import java.util.*;

public interface TransactionService {
    int createTransaction(Transaction transaction);
    void deleteForUserId(long userId);
    List<Transaction> getAllForUser(long userId);
}
