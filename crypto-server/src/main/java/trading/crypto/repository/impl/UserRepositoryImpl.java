package trading.crypto.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import trading.crypto.data.models.User;
import trading.crypto.repository.UserRepository;

import java.util.List;
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(User user) {
        String sql = "INSERT INTO users (username, cash) VALUES (?, ?);";
        return jdbcTemplate.update(sql, user.getUsername(), user.getCash());
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users;";
        return jdbcTemplate.query(sql, (rs, _) ->
                new User(rs.getLong(0),
                        rs.getString(1),
                        rs.getDouble(2)));
    }

    @Override
    public User findById(long id) {
        String sql = "SELECT * FROM users WHERE did = ?;";
        var user = jdbcTemplate.query(sql, (rs, _) ->
            new User(rs.getLong(0), rs.getString(1), rs.getDouble(2))).getFirst();
        return user;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?;";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {username},
                (rs, _) -> new User(
                        rs.getLong("did"),
                        rs.getString("username"),
                        rs.getDouble("cash")
                )
        );
    }

    @Override
    public boolean exists(String username){
        String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ?);";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, username));
    }

    @Override
    public double resetCashBalance(String username) {
        double defaultCash = 10000;
        String sql = "UPDATE users SET cash = ? WHERE username = ?;";
        jdbcTemplate.update(sql, defaultCash, username);
        return defaultCash;
    }

    @Override
    public double getCashBalance(String username) {
        String sql = "SELECT cash FROM users WHERE username = ?;";
        try {
            Double cash = jdbcTemplate.queryForObject(sql, Double.class, username);
            return cash != null ? cash : 0.0;
        } catch (EmptyResultDataAccessException e) {
            System.err.println("No user found with username: " + username);
            return 0.0;
        }
    }
}
