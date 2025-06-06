package trading.crypto.websocket.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;
import java.net.URI;

@Component
public class KrakenWebSocketClient extends WebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(KrakenWebSocketClient.class);

    public KrakenWebSocketClient(@Value("${kraken.ws.url}") String krakenWsUrl)
            throws Exception {
        super(new URI(krakenWsUrl));
    }
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        logger.info("Connected to kraken ws");
    }

    public void subscribeToPairs(List<String> pairNames){
        var subscriptionParams = new JSONObject().put("channel", "ticker")
                .put("symbol",
                        new JSONArray(pairNames.toArray()));
        String subscriptionMessage = buildWsMessage("subscribe", subscriptionParams);
        send(subscriptionMessage);
    }

    @Override
    public void onMessage(String message) {
        logger.info("Received message: {}", message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("Closing websocket: {}", reason);

    }

    @Override
    public void onError(Exception ex) {
        logger.error("Error in KrakenWebSocketClient: {}", ex.getMessage());
    }

    private String buildWsMessage(String method, JSONObject params) {
        return new JSONObject()
                .put("method", method)
                .put("params", params).toString();
    }
}
