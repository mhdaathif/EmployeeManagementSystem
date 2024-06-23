package com.aathif.web.domain.admin;

import com.aathif.web.domain.employee.EmployeeDTO;
import com.aathif.web.domain.security.model.User;
import com.aathif.web.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminResource {
    private final AdminService adminService;

    @PostMapping("/create-employee")
    public ResponseEntity<ApplicationResponseDTO> createUser(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(adminService.createUser(employeeDTO));
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminService.getUser(id));
    }
    @PutMapping("/change-user-status/{id}")
    public ResponseEntity<ApplicationResponseDTO> changeUserStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminService.changeUserStatus(id));
    }
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<ApplicationResponseDTO> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminService.deleteUser(id));
    }
}
