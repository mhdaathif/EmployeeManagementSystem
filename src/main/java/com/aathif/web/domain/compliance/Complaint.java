package com.aathif.web.domain.compliance;

import com.aathif.web.domain.model.BaseEntity;
import com.aathif.web.domain.security.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "\"Compliance\"")
public class Complaint extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    @Column(name = "complaint_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplaintType complianceType;
    @Column(name = "complaint_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplaintStatus complaintStatus;
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User employee;
}
