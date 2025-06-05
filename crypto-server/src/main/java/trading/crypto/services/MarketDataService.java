package trading.crypto.services;

import java.util.*;

public interface MarketDataService {

    List<String> getTopCryptoPairs(int limit);
}
