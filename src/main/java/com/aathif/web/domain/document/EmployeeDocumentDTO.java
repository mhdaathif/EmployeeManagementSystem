package com.aathif.web.domain.document;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class EmployeeDocumentDTO {
    @NonNull
    private Long employeeId;
    @NotNull
    private DocumentType type;
}
