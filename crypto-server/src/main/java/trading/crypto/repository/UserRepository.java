package trading.crypto.repository;

import trading.crypto.data.models.User;
import java.util.*;

public interface UserRepository {
    int save(User user);
    List<User> findAll();
    User findById(long id);
    User findByUsername(String username);
    boolean exists(String username);
    double resetCashBalance(String username);
    double getCashBalance(String username);
}
