package com.aathif.web.domain.job;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobRoleDTO {
    @NotBlank
    private String title;
    @NotNull
    @Min(0)
    private Double salary;
    @NotNull
    private Long departmentId;
}
