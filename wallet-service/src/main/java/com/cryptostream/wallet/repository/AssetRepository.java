package com.cryptostream.wallet.repository;

import com.cryptostream.wallet.domain.Asset;
import com.cryptostream.wallet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {
    Optional<Asset> findByUserAndSymbol(User user, String symbol);
    List<Asset> findByUser(User user);
}
