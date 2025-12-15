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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication Endpoints")
public class AuthController {

    private final WalletService walletService;

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Simple login returning user details (Demo)")
    public ResponseEntity<User> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(walletService.login(request.getEmail(), request.getPassword()));
    }
}
