package trading.crypto.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import trading.crypto.data.models.Transaction;
import trading.crypto.data.models.enums.TransactionType;
import trading.crypto.repository.TransactionRepository;

import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, date_produced, symbol, price_per_share, shares, action) " +
                "VALUES (?, ?, ?, ?, ?, ?::transaction_type);";
        return jdbcTemplate.update(sql,
                transaction.getUserId(),
                transaction.getDateProduced(),
                transaction.getSymbol(),
                transaction.getPricePerShare(),
                transaction.getAmountOfShares(),
                transaction.getAction().toString().toLowerCase());
    }

    @Override
    public List<Transaction> findAll() {
        return List.of();
    }

    @Override
    public List<Transaction> findByUserId(long userId){
        String sql = "SELECT * FROM transactions WHERE user_id = ?;";
        return jdbcTemplate.query(
                sql,
                new Object[] {userId},
                (rs, _) -> new Transaction(
                        rs.getLong("did"),
                        rs.getLong("user_id"),
                        rs.getDate("date_produced"),
                        rs.getString("symbol"),
                        rs.getDouble("price_per_share"),
                        rs.getDouble("shares"),
                        TransactionType.valueOf(rs.getString("action").toUpperCase()))
        );
    }

    @Override
    public Transaction find(long id) {
        return null;
    }

    @Override
    public void delete(long userId) {
        String sql = "DELETE FROM transactions WHERE user_id = ?;";
        jdbcTemplate.update(sql, userId);
    }
}
