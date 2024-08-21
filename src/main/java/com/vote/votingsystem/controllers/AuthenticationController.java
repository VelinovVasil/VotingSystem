package com.vote.votingsystem.controllers;

import com.vote.votingsystem.models.dtos.AuthenticationRequest;
import com.vote.votingsystem.models.dtos.AuthenticationResponse;
import com.vote.votingsystem.models.dtos.RegisterRequest;
import com.vote.votingsystem.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(this.authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(this.authenticationService.authenticate(request));
    }

}
