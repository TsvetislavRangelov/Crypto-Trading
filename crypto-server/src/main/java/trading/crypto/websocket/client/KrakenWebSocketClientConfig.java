package trading.crypto.websocket.client;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KrakenWebSocketClientConfig {

    private final KrakenWebSocketClient krakenWebSocketClient;

    public KrakenWebSocketClientConfig(KrakenWebSocketClient krakenWebSocketClient) {
        this.krakenWebSocketClient = krakenWebSocketClient;
    }

    @PostConstruct
    public void connect(){
        krakenWebSocketClient.connect();
    }
}
