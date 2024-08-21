package com.vote.votingsystem.models.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Middle name must not be blank")
    private String middleName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @Size(min = 10, max = 10, message = "Egn must be exactly 10 digits long")
    private String egn;

    @NotBlank(message = "Id number must not be blank")
    private String idNumber;

}
