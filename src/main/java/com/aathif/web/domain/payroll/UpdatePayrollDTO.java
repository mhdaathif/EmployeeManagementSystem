package com.aathif.web.domain.payroll;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdatePayrollDTO {
    private Double bonus;
    private Double deductions;
    @NotNull
    private LocalDate payDate;
}
