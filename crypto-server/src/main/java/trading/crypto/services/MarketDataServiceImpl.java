package trading.crypto.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
        KRAKEN_API_URL = krakenUrL;
    }

    public List<Pair> getTopCryptoPairs(int limit) {
        try {
            ResponseEntity<String> response = restTemplate.
                    getForEntity(KRAKEN_API_URL + "public/Ticker", String.class);
            List<Pair> pairs = getTopPairsByPrice(response);
            pairs = pairs.stream()
                    .sorted((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()))
                    .limit(limit)
                    .toList();
            // translate the REST format pairs to WS to use later when subscribing to the ticker channel.
            ResponseEntity<String> r = restTemplate.getForEntity(KRAKEN_API_URL + "public/AssetPairs?pair=" +
                            pairs.stream().map(Pair::getName).collect(Collectors.joining(",")),
                    String.class);
            translateRestPairNamesToWsPairNames(r, pairs);
            return pairs;
        } catch (Exception e) {
            logger.info("Caught exception in getTopCryptoPairs: {}", Arrays.toString(e.getStackTrace()));
        }
        return List.of();
    }

    private List<Pair> getTopPairsByPrice(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode assetPairs = mapper.readTree(response.getBody()).path("result");
        List<Pair> pairs = new ArrayList<>();
        assetPairs.properties().forEach(jsonNode -> {
            JsonNode val = jsonNode.getValue();
            String pairName = jsonNode.getKey();
            // xbt is missing in ws V2 but is present in REST asset pairs, therefore ignore it.
            https://docs.kraken.com/api/docs/guides/spot-ws-intro/
            if(pairName.endsWith("USD") && !pairName.contains("XBT")){
                JsonNode p = val.get("p");
                var first = p.get(0);
                var price = first.asDouble();
                pairs.add(new Pair(pairName, price));
            }
        });
        return pairs;
    }

    private void translateRestPairNamesToWsPairNames(ResponseEntity<String> response, List<Pair> pairs)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode assetPairs = mapper.readTree(response.getBody()).path("result");
        int i = 0;
        for(var node : assetPairs){
            var val = node.get("wsname").asText();
            pairs.get(i).setName(val);
            i++;
        }
    }
}
