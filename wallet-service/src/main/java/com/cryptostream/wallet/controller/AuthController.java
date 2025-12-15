package com.cryptostream.wallet.controller;

import com.cryptostream.wallet.domain.User;
import com.cryptostream.wallet.dto.AuthRequest;
import com.cryptostream.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final WalletService walletService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(walletService.login(request.getEmail(), request.getPassword()));
    }
}
