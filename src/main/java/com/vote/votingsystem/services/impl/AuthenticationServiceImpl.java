package com.vote.votingsystem.services.impl;

import com.vote.votingsystem.config.JwtService;
import com.vote.votingsystem.handler.exceptions.NoIdentityFoundException;
import com.vote.votingsystem.handler.exceptions.UserAlreadyExistsException;
import com.vote.votingsystem.handler.exceptions.UserNotFoundException;
import com.vote.votingsystem.models.dtos.AuthenticationRequest;
import com.vote.votingsystem.models.dtos.AuthenticationResponse;
import com.vote.votingsystem.models.dtos.RegisterRequest;
import com.vote.votingsystem.models.entities.PersonalInfo;
import com.vote.votingsystem.models.entities.Role;
import com.vote.votingsystem.models.entities.User;
import com.vote.votingsystem.repositories.PersonalInfoRepository;
import com.vote.votingsystem.repositories.UserRepository;
import com.vote.votingsystem.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PersonalInfoRepository personalInfoRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        if (this.userRepository.existsByEmail(request.getEmail())) {

            throw new UserAlreadyExistsException("User with this email already exists");

        }

        if (!this.personalInfoRepository.existsByEgnAndIdNumberAndFirstNameAndMiddleNameAndLastName(
                request.getEgn(),
                request.getIdNumber(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getLastName()
        )) {

            throw new NoIdentityFoundException("The provided info does not correspond to a person");

        }

        User user = this.modelMapper.map(request, User.class);
        user.setId(null);
        user.setPassword(this.encoder.encode(request.getPassword()));

        PersonalInfo personalInfo = this.personalInfoRepository.getPersonalInfoByEgn(request.getEgn()).get();

        user.setPersonalInfo(personalInfo);

        if (this.userRepository.count() == 0) {

            user.setRole(Role.ADMIN);

        } else {

            user.setRole(Role.USER);

        }

        this.userRepository.saveAndFlush(user);

        String jwtToken = this.jwtService.generateToken(user, user.getId());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = this.userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("No user with such username found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        //User user = this.userRepository.findUserByUsername(request.getUsername())
        //        .orElseThrow(() -> new UserNotFoundException("No user with such username found"));

        String jwtToken = this.jwtService.generateToken(user, user.getId());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
