package com.aathif.web.domain.compliance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComplaintDTO {
    @NotNull
    private ComplaintType complaintType;
    @NotBlank
    private String description;
}
