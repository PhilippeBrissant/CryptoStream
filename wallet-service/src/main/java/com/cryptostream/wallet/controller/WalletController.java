package com.cryptostream.wallet.controller;

import com.cryptostream.wallet.domain.Asset;
import com.cryptostream.wallet.domain.Transaction;
import com.cryptostream.wallet.domain.User;
import com.cryptostream.wallet.service.WalletService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final com.cryptostream.wallet.service.ReportProducerService reportProducerService;

    @PostMapping("/trade/buy")
    public ResponseEntity<Transaction> buy(@RequestBody TradeRequest request) {
        return ResponseEntity.ok(walletService.buyAsset(
                request.getUserId(),
                request.getSymbol(),
                request.getAmount(),
                request.getPrice()));
    }

    @PostMapping("/trade/sell")
    public ResponseEntity<Transaction> sell(@RequestBody TradeRequest request) {
        return ResponseEntity.ok(walletService.sellAsset(
                request.getUserId(),
                request.getSymbol(),
                request.getAmount(),
                request.getPrice()));
    }

    @GetMapping("/{userId}/portfolio")
    public ResponseEntity<List<Asset>> getPortfolio(@PathVariable UUID userId) {
        return ResponseEntity.ok(walletService.getPortfolio(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(walletService.getUser(userId));
    }

    @PostMapping("/{userId}/report")
    public ResponseEntity<Void> requestReport(@PathVariable UUID userId) {
        reportProducerService.requestReport(userId);
        return ResponseEntity.accepted().build();
    }

    @Data
    public static class TradeRequest {
        private UUID userId;
        private String symbol;
        private BigDecimal amount;
        private BigDecimal price;
    }
}
