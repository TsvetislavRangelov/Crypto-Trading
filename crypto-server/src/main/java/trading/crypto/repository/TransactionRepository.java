package trading.crypto.repository;

import trading.crypto.data.models.Transaction;
import java.util.*;

public interface TransactionRepository {
    int save(Transaction transaction);
    List<Transaction> findAll();
    Transaction find(long id);
    int delete(long id);
}
