package com.vote.votingsystem.services;

import com.vote.votingsystem.models.dtos.AuthenticationRequest;
import com.vote.votingsystem.models.dtos.AuthenticationResponse;
import com.vote.votingsystem.models.dtos.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
