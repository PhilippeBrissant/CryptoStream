package com.cryptostream.market.controller;

import com.cryptostream.market.service.MarketDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
@Tag(name = "Market", description = "Crypto Market Data Endpoints")
public class MarketController {

    private final MarketDataService marketDataService;

    @GetMapping("/prices")
    @Operation(summary = "Get Current Prices", description = "Get the latest snapshot of crypto prices linked to CoinGecko")
    public Mono<Map> getParams() {
        return marketDataService.getPrices();
    }
}
