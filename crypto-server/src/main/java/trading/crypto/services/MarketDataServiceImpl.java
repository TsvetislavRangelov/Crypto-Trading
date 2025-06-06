package trading.crypto.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import trading.crypto.data.models.Pair;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MarketDataServiceImpl implements MarketDataService {

    private static final Logger logger = LoggerFactory.getLogger(MarketDataServiceImpl.class);
    private static String KRAKEN_API_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    public MarketDataServiceImpl(@Value("${kraken.rest.url}") String krakenUrL) {
        this.KRAKEN_API_URL = krakenUrL;
    }

    public List<String> getTopCryptoPairs(int limit) {
        try {
            ResponseEntity<String> response = restTemplate.
                    getForEntity(KRAKEN_API_URL + "public/Ticker", String.class);
            if (response != null) {
                List<Pair> pairs = getTopPairsByVolume(response);
                return pairs.stream()
                        .sorted((p1, p2) -> Double.compare(p2.getVolume(), p1.getVolume()))
                        .limit(limit)
                        .map(Pair::getName)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            logger.info("Caught exception in getTopCryptoPairs: {}", Arrays.toString(e.getStackTrace()));
        }
        return List.of();
    }

    private List<Pair> getTopPairsByVolume(ResponseEntity<String> response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode assetPairs = mapper.readTree(response.getBody()).path("result");
        List<Pair> pairs = new ArrayList<>();
        assetPairs.properties().forEach(jsonNode -> {
            JsonNode val = jsonNode.getValue();
            String pairName = jsonNode.getKey();
            String base = val.path("base").asText();
            double volume = Double.parseDouble(val.path("v").get(1).asText());
            pairs.add(new Pair(pairName, base, "", volume));
        });
        return pairs;
    }
}
