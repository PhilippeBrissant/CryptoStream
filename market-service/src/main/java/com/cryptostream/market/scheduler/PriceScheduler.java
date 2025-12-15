package com.cryptostream.market.scheduler;

import com.cryptostream.market.handler.PriceWebSocketHandler;
import com.cryptostream.market.service.MarketDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceScheduler {

    private final MarketDataService marketDataService;
    private final PriceWebSocketHandler webSocketHandler;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 10000) // 10 seconds
    public void fetchAndBroadcastPrices() {
        marketDataService.getPrices()
                .subscribe(prices -> {
                    try {
                        String json = objectMapper.writeValueAsString(prices);
                        webSocketHandler.broadcast(json);
                        log.info("Broadcasted prices: {}", json);
                    } catch (Exception e) {
                        log.error("Error broadcasting prices", e);
                    }
                });
    }
}
