package com.cryptostream.wallet.service;

import com.cryptostream.wallet.domain.Asset;
import com.cryptostream.wallet.domain.Transaction;
import com.cryptostream.wallet.domain.User;
import com.cryptostream.wallet.repository.AssetRepository;
import com.cryptostream.wallet.repository.TransactionRepository;
import com.cryptostream.wallet.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

        private final UserRepository userRepository;
        private final AssetRepository assetRepository;
        private final TransactionRepository transactionRepository;

        @Transactional
        public Transaction buyAsset(UUID userId, String symbol, BigDecimal amount, BigDecimal price) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                BigDecimal totalCost = amount.multiply(price);

                if (user.getBalanceUsd().compareTo(totalCost) < 0) {
                        throw new RuntimeException("Insufficient funds");
                }

                user.setBalanceUsd(user.getBalanceUsd().subtract(totalCost));
                userRepository.save(user);

                Asset asset = assetRepository.findByUserAndSymbol(user, symbol)
                                .orElse(Asset.builder()
                                                .user(user)
                                                .symbol(symbol)
                                                .amount(BigDecimal.ZERO)
                                                .averagePrice(BigDecimal.ZERO)
                                                .build());

                // Update average price: (oldTotal + newTotal) / (oldAmount + newAmount)
                BigDecimal oldTotalValue = asset.getAmount().multiply(asset.getAveragePrice());
                BigDecimal newTotalValue = oldTotalValue.add(totalCost);
                BigDecimal newAmount = asset.getAmount().add(amount);

                if (newAmount.compareTo(BigDecimal.ZERO) > 0) {
                        asset.setAveragePrice(newTotalValue.divide(newAmount, 8, RoundingMode.HALF_UP));
                }
                asset.setAmount(newAmount);
                assetRepository.save(asset);

                Transaction transaction = Transaction.builder()
                                .user(user)
                                .type(Transaction.TransactionType.BUY)
                                .symbol(symbol)
                                .quantity(amount)
                                .priceAtMoment(price)
                                .timestamp(LocalDateTime.now())
                                .build();

                return transactionRepository.save(transaction);
        }

        @Transactional
        public Transaction sellAsset(UUID userId, String symbol, BigDecimal amount, BigDecimal price) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                Asset asset = assetRepository.findByUserAndSymbol(user, symbol)
                                .orElseThrow(() -> new RuntimeException("Asset not found"));

                if (asset.getAmount().compareTo(amount) < 0) {
                        throw new RuntimeException("Insufficient asset balance");
                }

                BigDecimal totalRevenue = amount.multiply(price);

                asset.setAmount(asset.getAmount().subtract(amount));
                assetRepository.save(asset);

                user.setBalanceUsd(user.getBalanceUsd().add(totalRevenue));
                userRepository.save(user);

                Transaction transaction = Transaction.builder()
                                .user(user)
                                .type(Transaction.TransactionType.SELL)
                                .symbol(symbol)
                                .quantity(amount)
                                .priceAtMoment(price)
                                .timestamp(LocalDateTime.now())
                                .build();

                return transactionRepository.save(transaction);
        }

        public List<Asset> getPortfolio(UUID userId) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));
                return assetRepository.findByUser(user);
        }

        public User getUser(UUID userId) {
                return userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));
        }

        public User login(String email, String password) {
                // In real app: Use BCrypt and JWT. Here: Simple match for demo.
                return userRepository.findByEmail(email)
                                .filter(u -> u.getPasswordHash().equals(password))
                                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        }
}
