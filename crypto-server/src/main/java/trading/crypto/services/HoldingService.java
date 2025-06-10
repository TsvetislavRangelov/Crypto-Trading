package trading.crypto.services;
import java.util.*;
import trading.crypto.data.models.Holding;

public interface HoldingService {
    void registerHolding(Holding holding);
    boolean deleteHolding(long userId, String symbol);
    void deleteForUserId(long userId);
    void updateHolding(Holding holding);
    List<Holding> getALlForUserId(long id);
    Holding getById(long id);
    Holding getByUserIdAndSymbol(long userId, String symbol);
}
