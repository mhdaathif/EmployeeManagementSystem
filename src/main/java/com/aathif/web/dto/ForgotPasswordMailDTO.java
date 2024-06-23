package com.aathif.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ForgotPasswordMailDTO {
    private String name;
    private String resetLink;
}
