package com.aathif.web.domain.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResetForgotPasswordDTO {
    @NotBlank
    private String password;
    @NotBlank
    private String conformPassword;
}
