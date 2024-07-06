package com.aathif.web.domain.employee;

import com.aathif.web.domain.security.model.User;
import com.aathif.web.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeResource {
    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<ApplicationResponseDTO> createUser(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.createUser(employeeDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(employeeService.getUsers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.getUser(id));
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<ApplicationResponseDTO> changeUserStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.changeUserStatus(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationResponseDTO> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.deleteUser(id));
    }

}
