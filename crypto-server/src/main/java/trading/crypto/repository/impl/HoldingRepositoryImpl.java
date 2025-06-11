package trading.crypto.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import trading.crypto.data.models.Holding;
import trading.crypto.repository.HoldingRepository;

import java.util.List;

@Repository
public class HoldingRepositoryImpl implements HoldingRepository {

    private final JdbcTemplate jdbcTemplate;

    public HoldingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Holding findById(long id) {
        Holding holding = null;
        if(exists(id)) {
            String sql = "SELECT * FROM holdings WHERE did = ?;";
            holding = jdbcTemplate.queryForObject(sql, new Object [] {id}, (rs, _) -> new Holding(rs.getLong("did"),
                    rs.getLong("user_id"),
                    rs.getDouble("invested"),
                    rs.getString("symbol"),
                    rs.getDouble("shares"),
                    rs.getDouble("avg_price")));
        }
        return holding;
    }

    @Override
    public Holding findByUserIdAndSymbol(long userId, String symbol) {
        Holding holding = null;
        boolean exists = existsForUser(userId, symbol);
        if(exists) {
            String sql = "SELECT * FROM holdings WHERE user_id = ? AND symbol = ?;";
            holding = jdbcTemplate. queryForObject(sql, new Object [] {userId, symbol}, (rs, _) -> new Holding(rs.getLong("did"),
                    rs.getLong("user_id"),
                    rs.getDouble("invested"),
                    rs.getString("symbol"),
                    rs.getDouble("shares"),
                    rs.getDouble("avg_price")));
        }
        return holding;
    }

    @Override
    public List<Holding> findAllForUserId(long userId) {
        String sql = "SELECT * FROM holdings WHERE user_id = ?;";
        var holdings = jdbcTemplate.query(sql, new Object[] {userId}, (rs, _) ->
                new Holding(rs.getLong("did"),
                        rs.getLong("user_id"),
                        rs.getDouble("invested"),
                        rs.getString("symbol"),
                        rs.getDouble("shares"),
                        rs.getDouble("avg_price")));
        return holdings;
    }

    @Override
    public boolean delete(long userId, String symbol) {
        String sql = "DELETE FROM holdings WHERE user_id = ? AND symbol = ?;";
        return jdbcTemplate.update(sql, userId, symbol) == 1;
    }

    @Override
    public void deleteForUserId(long userId) {
        String sql = "DELETE FROM holdings WHERE user_id = ?;";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public void save(Holding holding) {
        String sql = "INSERT INTO holdings (user_id, invested, symbol, shares, avg_price) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, holding.getUserId(), holding.getInvested(), holding.getSymbol(), holding.getShareAmount(), holding.getAvg()) ;
    }

    @Override
    public void update(Holding holding) {
        String sql = "UPDATE holdings SET invested = ?, symbol = ?, shares = ?, avg_price = ? WHERE did = ?;";
        jdbcTemplate.update(sql, holding.getInvested(), holding.getSymbol(), holding.getShareAmount(), holding.getAvg(), holding.getId());
    }

    private boolean exists(long id){
        String sql = "SELECT EXISTS(SELECT 1 FROM holdings WHERE did = ?);";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id));
    }

    private boolean existsForUser(long userId, String symbol){
        String sql = "SELECT EXISTS(SELECT 1 FROM holdings WHERE user_id = ? AND symbol = ?);";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, userId, symbol));
    }
}
