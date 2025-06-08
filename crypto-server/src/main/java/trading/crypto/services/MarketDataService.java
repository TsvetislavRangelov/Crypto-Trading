package trading.crypto.services;

import trading.crypto.data.models.Pair;

import java.util.*;

public interface MarketDataService {

    List<Pair> getTopCryptoPairs(int limit);
}
