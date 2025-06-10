package trading.crypto.repository;
import java.util.*;

import trading.crypto.data.models.Holding;

public interface HoldingRepository {
    Holding findById(long id);
    Holding findByUserIdAndSymbol(long userId, String symbol);
    List<Holding> findAllForUserId(long userId);
    boolean delete(long userId, String symbol);
    void deleteForUserId(long userId);
    void save(Holding holding);
    void update(Holding holding);
}
