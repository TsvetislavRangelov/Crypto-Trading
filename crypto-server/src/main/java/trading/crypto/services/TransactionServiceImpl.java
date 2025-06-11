package trading.crypto.services;

import org.springframework.stereotype.Service;
import trading.crypto.data.models.Transaction;
import trading.crypto.repository.TransactionRepository;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public int createTransaction(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

    @Override
    public void deleteForUserId(long userId) {
        this.transactionRepository.delete(userId);
    }

    @Override
    public List<Transaction> getAllForUser(long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
