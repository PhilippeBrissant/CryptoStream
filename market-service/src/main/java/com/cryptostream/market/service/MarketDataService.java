package com.cryptostream.market.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarketDataService {

    private final CoinGeckoClient coinGeckoClient;
    
    // Simple in-memory cache for fallback
    private Map<String, Object> lastKnownPrices = new HashMap<>();

    @CircuitBreaker(name = "coingecko", fallbackMethod = "fallbackGetPrices")
    public Mono<Map> getPrices() {
        return coinGeckoClient.getPrices()
                .doOnNext(prices -> lastKnownPrices = prices);
    }

    public Mono<Map> fallbackGetPrices(Throwable t) {
        log.error("CoinGecko API unavailable, returning cached prices: {}", t.getMessage());
        return Mono.just(lastKnownPrices);
    }
}
