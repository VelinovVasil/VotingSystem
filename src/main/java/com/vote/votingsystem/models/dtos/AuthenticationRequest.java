package com.vote.votingsystem.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Email(message = "A valid email must be provided")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

}
