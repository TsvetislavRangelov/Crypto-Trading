package trading.crypto.repository.impl;

import org.springframework.stereotype.Repository;
import trading.crypto.data.models.Transaction;
import trading.crypto.repository.TransactionRepository;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {


    @Override
    public int save(Transaction transaction) {
        return 0;
    }

    @Override
    public List<Transaction> findAll() {
        return List.of();
    }

    @Override
    public Transaction find(long id) {
        return null;
    }

    @Override
    public int delete(long id) {
        return 0;
    }
}
