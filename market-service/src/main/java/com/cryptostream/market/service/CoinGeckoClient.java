package com.cryptostream.market.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class CoinGeckoClient {

    private final WebClient webClient;

    public CoinGeckoClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.coingecko.com/api/v3").build();
    }

    public Mono<Map> getPrices() {
        return webClient.get()
                .uri("/simple/price?ids=bitcoin,ethereum,solana&vs_currencies=usd")
                .retrieve()
                .bodyToMono(Map.class);
    }
}
