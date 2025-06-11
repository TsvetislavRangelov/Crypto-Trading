package trading.crypto.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import trading.crypto.components.TickerMessageProcessor;
import trading.crypto.data.models.Ticker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TickerServiceImpl implements TickerService {

    private final Logger logger = LoggerFactory.getLogger(TickerServiceImpl.class);
    private final Map<String, Ticker> tickerCache;
    private final SimpMessagingTemplate messagingTemplate;
    private final TickerMessageProcessor tickerMessageProcessor;

    @Autowired
    public TickerServiceImpl(SimpMessagingTemplate messagingTemplate, TickerMessageProcessor tickerMessageProcessor) {
        this.messagingTemplate = messagingTemplate;
        this.tickerCache = new ConcurrentHashMap<>(20);
        this.tickerMessageProcessor = tickerMessageProcessor;
    }

    @Override
    public void forwardTickerMessage(String topic, String message) {
        Ticker ticker = null;
        try {
            ticker = tickerMessageProcessor.deserializeTicker(message);
        } catch (JsonProcessingException e) {
            // ideally we want to send an error back to the client as well but dont wanna bother.
            logger.error("Error in ticker deserialization: {}", e.getMessage());
        }
        if (ticker == null) {
            return;
        }
        synchronized (tickerCache) {
            if(tickerCache.containsKey(ticker.getSymbol())) {
                tickerCache.put(ticker.getSymbol(), ticker);
            }
            if(tickerCache.size() < 20){
                tickerCache.put(ticker.getSymbol(), ticker);
            }
            // get the current cheapest ticker.
            Optional<Ticker> currentLowestTicker = tickerCache.values().
                    stream().
                    min(Comparator.comparingDouble(Ticker::getPrice));
            Ticker finalTicker = ticker; // this needs to be final.
            currentLowestTicker.ifPresent(t -> {
                if (finalTicker.getPrice() > t.getPrice() && tickerCache.size() == 20) {
                    // update the hashmap with the current ticker since it costs more.
                    tickerCache.remove(t.getSymbol());
                    tickerCache.put(finalTicker.getSymbol(), finalTicker);
                }
            });
        }
        messagingTemplate.convertAndSend(topic, ticker);
    }

    @Override
    public List<Ticker> getTop20Tickers() {
        return new ArrayList<>(tickerCache.values());
    }
}
