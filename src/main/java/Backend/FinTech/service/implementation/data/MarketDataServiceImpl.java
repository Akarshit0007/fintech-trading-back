package Backend.FinTech.service.implementation.data;

import Backend.FinTech.service.Interface.data.MarketDataSerive;
import Backend.FinTech.websocket.CustomWebSocketHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class MarketDataServiceImpl implements MarketDataSerive {

    @Value("${alltick.token}")
    private String apiToken;

    private final CustomWebSocketHandler webSocketHandler;
    private WebSocket webSocket;
    private final HttpClient client = HttpClient.newHttpClient();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> heartbeatTask;
    private volatile boolean running = true;

    public MarketDataServiceImpl(CustomWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @PostConstruct
    @Override
    public void connect() {
        scheduler.execute(this::connectWithRetry);
    }

    private void connectWithRetry() {
        String url = "wss://quote.alltick.co/quote-b-ws-api?token=" + apiToken;

        while (running) {
            try {
                System.out.println("Connecting to AllTick WebSocket...");
                webSocket = client.newWebSocketBuilder()
                        .buildAsync(URI.create(url), new AllTickListener())
                        .join();
                break;
            } catch (Exception e) {
                System.err.println("Connection failed. Retrying in 5s... Error: " + e.getMessage());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private class AllTickListener implements WebSocket.Listener {
        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("AllTick Connected! Sending subscription.");
            String subPayload = "{\"cmd_id\":22004,\"seq_id\":1,\"trace\":\"test\",\"data\":{\"symbol_list\":[" +
                    "{\"code\":\"USDJPY\"}," +
                    "{\"code\":\"XAUUSD\"}," +
                    "{\"code\":\"USOIL\"}," +
                    "{\"code\":\"HK50\"}," +
                    "{\"code\":\"BTCUSDT\"}," +
                    "{\"code\":\"DJI\"}," +
                    "{\"code\":\"TSLA\"}," +
                    "{\"code\":\"00700\"}," +
                    "{\"code\":\"sh000001\"}," +
                    "{\"code\":\"sz399001\"}" +
                    "]}}";
            webSocket.sendText(subPayload, true);

            heartbeatTask = scheduler.scheduleAtFixedRate(() -> {
                if (webSocket != null && !webSocket.isInputClosed()) {
                    String heartbeatPayload = "{\"cmd_id\":22000,\"seq_id\":2,\"trace\":\"heartbeat\",\"data\":{}}";
                    webSocket.sendText(heartbeatPayload, true);
                }
            }, 10, 10, TimeUnit.SECONDS);

            WebSocket.Listener.super.onOpen(webSocket);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            String tickMessage = data.toString();
//            System.out.println("Received Live Tick: " + tickMessage);

            // Only broadcast actual price ticks (cmd_id: 22998) to the frontend
            if (tickMessage.contains("\"cmd_id\":22998")) {
                webSocketHandler.broadcastMessage(tickMessage);
            }

            return WebSocket.Listener.super.onText(webSocket, data, last);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            System.err.println("AllTick WebSocket Error: " + error.getMessage());
            WebSocket.Listener.super.onError(webSocket, error);
        }

        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            System.out.println("AllTick Closed: " + reason + ". Reconnecting...");
            if (heartbeatTask != null) {
                heartbeatTask.cancel(true);
            }
            if (running) {
                scheduler.schedule(MarketDataServiceImpl.this::connectWithRetry, 5, TimeUnit.SECONDS);
            }
            return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
        }
    }

    @PreDestroy
    public void cleanup() {
        running = false;
        if (heartbeatTask != null) {
            heartbeatTask.cancel(true);
        }
        scheduler.shutdown();
        if (webSocket != null) {
            webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "App shutting down");
        }
    }
}