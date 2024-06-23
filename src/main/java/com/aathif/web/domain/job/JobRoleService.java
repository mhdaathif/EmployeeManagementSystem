package com.aathif.web.domain.job;

import com.aathif.web.domain.department.DepartmentRepository;
import com.aathif.web.dto.ApplicationResponseDTO;
import com.aathif.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JobRoleService {
    private final JobRoleRepository jobRoleRepository;
    private final DepartmentRepository departmentRepository;

    public ApplicationResponseDTO createJobRole(JobRoleDTO jobRoleDTO) {
        if (departmentRepository.findById(jobRoleDTO.getDepartmentId()).isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_DEPARTMENT_ID", "Invalid Department Id");
        }
        if (jobRoleRepository.findByTitleAndDepartmentId(jobRoleDTO.getTitle(), jobRoleDTO.getDepartmentId()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "JOB_ROLE_ALREADY_EXIST", "Job Role Already Exist");
        }
        jobRoleRepository.save(JobRole.builder()
                .title(jobRoleDTO.getTitle())
                .salary(jobRoleDTO.getSalary())
                .departmentId(jobRoleDTO.getDepartmentId())
                .build());
        return new ApplicationResponseDTO(HttpStatus.CREATED, "NEW_JOB_ROLE_CREATED_SUCCESSFULLY", "New Job Role Created Successfully!");
    }

    public List<JobRole> getJobRoles() {
        return jobRoleRepository.findAllByTitleNot("Admin");
    }

    public JobRole getJobRoleById(Long id) {
        return jobRoleRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_JOB_ROLE_ID", "Invalid Job Role Id"));
    }

    public List<JobRole> getJobRoleByTitle(String title) {
        List<JobRole> jobRoles = jobRoleRepository.findAllByTitle(title);
        if (jobRoles.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_JOB_ROLE_TITLE", "Invalid Job Role Title");
        }
        return jobRoles;
    }

    public List<JobRole> getJobRoleByDepartment(Long id) {
        if (departmentRepository.findById(id).isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_DEPARTMENT_ID", "Invalid Department Id");
        }
        return jobRoleRepository.findAllByDepartmentId(id);
    }

    public ApplicationResponseDTO updateJobRole(Long id, JobRoleDTO jobRoleDTO) {
        JobRole jobRole = jobRoleRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_JOB_ROLE", "Invalid Job Role"));
        if (departmentRepository.findById(jobRoleDTO.getDepartmentId()).isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_DEPARTMENT_ID", "Invalid Department Id");
        }
        jobRoleRepository.findByTitleAndDepartmentId(jobRoleDTO.getTitle(), jobRoleDTO.getDepartmentId()).ifPresent(existingDepartment -> {
            if (!((jobRole.getTitle().equals(jobRoleDTO.getTitle()) && (jobRole.getDepartmentId().equals(jobRoleDTO.getDepartmentId()))))) {
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "JOB_ROLE_ALREADY_EXISTS", "Job Role Already Exists");
            }
        });
        jobRole.setTitle(jobRoleDTO.getTitle());
        jobRole.setSalary(jobRole.getSalary());
        jobRole.setDepartmentId(jobRoleDTO.getDepartmentId());
        jobRoleRepository.save(jobRole);
        return new ApplicationResponseDTO(HttpStatus.CREATED, "JOB_ROLE_UPDATE_SUCCESSFULLY", "Job Role Update Successfully");
    }
}
