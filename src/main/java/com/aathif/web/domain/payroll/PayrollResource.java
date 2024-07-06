package com.aathif.web.domain.payroll;

import com.aathif.web.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payroll")
public class PayrollResource {
    private final PayrollService payrollService;

    @PostMapping("/add")
    public ResponseEntity<ApplicationResponseDTO> addPayroll(@RequestBody PayrollDTO payrollDTO) {
        return ResponseEntity.ok(payrollService.addPayroll(payrollDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Payroll>> getPayrolls() {
        return ResponseEntity.ok(payrollService.getPayrolls());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Payroll> getPayroll(@PathVariable("id") Long id) {
        return ResponseEntity.ok(payrollService.getPayroll(id));
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<Payroll>> getPayrolls(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(payrollService.getPayrolls(userId));
    }

    @GetMapping("/get/date/{localDate}")
    public ResponseEntity<List<Payroll>> getPayrolls(@PathVariable("localDate") LocalDate localDate) {
        return ResponseEntity.ok(payrollService.getPayrolls(localDate));
    }

    @GetMapping("/get/{id}/{localDate}")
    public ResponseEntity<List<Payroll>> getPayrolls(@PathVariable("id") Long id, @PathVariable("localDate") LocalDate localDate) {
        return ResponseEntity.ok(payrollService.getPayrolls(id, localDate));
    }

    @GetMapping("/get-own")
    public ResponseEntity<List<Payroll>> getOwnPayrolls() {
        return ResponseEntity.ok(payrollService.getOwnPayrolls());
    }

    @GetMapping("/get-own/{id}")
    public ResponseEntity<Payroll> getOwnPayroll(@PathVariable("id") Long id) {
        return ResponseEntity.ok(payrollService.getOwnPayroll(id));
    }

    @GetMapping("/get-own/date/{localDate}")
    public ResponseEntity<List<Payroll>> getOwnPayroll(@PathVariable("localDate") LocalDate localDate) {
        return ResponseEntity.ok(payrollService.getOwnPayroll(localDate));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updatePayroll(@PathVariable("id") Long id, @RequestBody UpdatePayrollDTO updatePayrollDTO) {
        return ResponseEntity.ok(payrollService.updatePayroll(id, updatePayrollDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationResponseDTO> deletePayroll(@PathVariable("id") Long id) {
        return ResponseEntity.ok(payrollService.deletePayroll(id));
    }

}
