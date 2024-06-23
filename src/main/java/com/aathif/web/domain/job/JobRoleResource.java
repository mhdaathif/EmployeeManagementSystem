package com.aathif.web.domain.job;

import com.aathif.web.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job-role")
public class JobRoleResource {
    private final JobRoleService jobRoleService;

    @PostMapping("/create")
    private ResponseEntity<ApplicationResponseDTO> createJobRole(@Valid @RequestBody JobRoleDTO jobRoleDTO){
        return ResponseEntity.ok(jobRoleService.createJobRole(jobRoleDTO));
    }

    @GetMapping("/get")
    private ResponseEntity<List<JobRole>> getJobRoles(){
        return ResponseEntity.ok(jobRoleService.getJobRoles());
    }

    @GetMapping("/get/{id}")
    private ResponseEntity<JobRole> getJobRoleById(@PathVariable("id") Long id){
        return ResponseEntity.ok(jobRoleService.getJobRoleById(id));
    }

    @GetMapping("/get-by-title/{title}")
    private ResponseEntity<List<JobRole>> getJobRoleByTitle(@PathVariable("title") String title){
        return ResponseEntity.ok(jobRoleService.getJobRoleByTitle(title));
    }

    @GetMapping("/get-by-departmentId/{id}")
    private ResponseEntity<List<JobRole>> getJobRoleByDepartment(@PathVariable("id") Long id){
        return ResponseEntity.ok(jobRoleService.getJobRoleByDepartment(id));
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<ApplicationResponseDTO> updateJobRole(@PathVariable("id") Long id, @Valid @RequestBody JobRoleDTO jobRoleDTO){
        return ResponseEntity.ok(jobRoleService.updateJobRole(id, jobRoleDTO));
    }

}
