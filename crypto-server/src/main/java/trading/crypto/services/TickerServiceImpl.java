package trading.crypto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import trading.crypto.data.models.Ticker;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class TickerServiceImpl implements TickerService {

    private final Map<String, Ticker> tickerCache = new ConcurrentHashMap<>(20);
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TickerServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override

    public void forwardTickerMessage(String topic, String message) {
        messagingTemplate.convertAndSend(topic, message);
    }

    @Override
    public List<Ticker> getTop20Tickers() {
        return tickerCache.values().stream().collect(Collectors.toList());
    }
}
