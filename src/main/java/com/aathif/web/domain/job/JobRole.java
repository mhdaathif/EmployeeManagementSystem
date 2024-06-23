package com.aathif.web.domain.job;

import com.aathif.web.domain.department.Department;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "\"JobRole\"")
public class JobRole {
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
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "salary", nullable = false)
    private Double salary;
    @Column(name = "department_id", nullable = false)
    private Long departmentId;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Department department;
}
