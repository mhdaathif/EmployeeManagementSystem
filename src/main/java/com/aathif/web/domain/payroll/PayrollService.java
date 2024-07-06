package com.aathif.web.domain.payroll;

import com.aathif.web.domain.security.model.User;
import com.aathif.web.domain.security.repos.UserRepository;
import com.aathif.web.domain.user.UserService;
import com.aathif.web.dto.ApplicationResponseDTO;
import com.aathif.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PayrollService {
    private final PayrollRepository payrollRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ApplicationResponseDTO addPayroll(PayrollDTO payrollDTO) {
        User user = userRepository.findById(payrollDTO.getEmployeeId()).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_EMPLOYEE_ID", "Invalid Employee Id"));
        Payroll payroll = new Payroll();
//        Double netPay = 0.0;
//        netPay = netPay + user.getJobRole().getSalary();
//
//        if (payrollDTO.getBonus() != null) {
//            netPay = netPay + payrollDTO.getBonus();
//            payroll.setBonus(payrollDTO.getBonus());
//        }
//        if (payrollDTO.getDeductions() != null) {
//            netPay = netPay - payrollDTO.getDeductions();
//            payroll.setDeductions(payrollDTO.getDeductions());
//        }
        payroll.setEmployeeId(user.getId());
        payroll.setBonus(payrollDTO.getBonus() == null ? 0.0 : payrollDTO.getBonus());
        payroll.setDeductions(payrollDTO.getDeductions() == null ? 0.0 : payrollDTO.getDeductions());
        payroll.setNetPay(getNetPay(user.getJobRole().getSalary(), payrollDTO.getBonus(), payrollDTO.getDeductions()));
        payroll.setPayDate(payrollDTO.getPayDate());
        payrollRepository.save(payroll);
        return new ApplicationResponseDTO(HttpStatus.CREATED, "PAYROLL_CREATED_SUCCESSFULLY", "Payroll Create Successfully");
    }

    public List<Payroll> getPayrolls() {
        return payrollRepository.findAll();
    }

    public Payroll getPayroll(Long id) {
        return payrollRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_PAYROLL_ID", "Invalid Payroll Id"));
    }

    public List<Payroll> getPayrolls(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "USER_ID_NOT_FOUND", "User Id Not Found"));
        return payrollRepository.findAllByEmployeeId(user.getId());
    }

    public List<Payroll> getPayrolls(LocalDate payDate) {
        return payrollRepository.findAllByPayDate(payDate);
    }

    public List<Payroll> getPayrolls(Long userId, LocalDate payDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "USER_ID_NOT_FOUND", "User Id Not Found"));
        return payrollRepository.findAllByEmployeeIdAndPayDate(user.getId(), payDate);
    }

    public List<Payroll> getOwnPayrolls() {
        User user = userService.getCurrentUser();
        return payrollRepository.findAllByEmployeeId(user.getId());
    }

    public Payroll getOwnPayroll(Long id) {
        Payroll payroll = payrollRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_PAYROLL_ID", "Invalid Payroll Id"));
        User user = userService.getCurrentUser();
        if (!payroll.getEmployeeId().equals(user.getId())) {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "UNAUTHORIZED_LEAVE", "Invalid User");
        }
        return payroll;
    }

    public List<Payroll> getOwnPayroll(LocalDate payDate) {
        User user = userService.getCurrentUser();
        return payrollRepository.findAllByEmployeeIdAndPayDate(user.getId(), payDate);
    }

    public ApplicationResponseDTO updatePayroll(Long id, UpdatePayrollDTO updatePayrollDTO) {
        Payroll payroll = payrollRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_PAYROLL_ID", "Invalid Payroll Id"));
//        Double netPay = 0.0;
//        netPay = netPay + payroll.getUser().getJobRole().getSalary();
//
//        if (updatePayrollDTO.getBonus() != null) {
//            netPay = netPay + updatePayrollDTO.getBonus();
//            payroll.setBonus(updatePayrollDTO.getBonus());
//        }
//        if (updatePayrollDTO.getDeductions() != null) {
//            netPay = netPay - updatePayrollDTO.getDeductions();
//            payroll.setDeductions(updatePayrollDTO.getDeductions());
//        }
        payroll.setBonus(updatePayrollDTO.getBonus() == null ? payroll.getBonus() : updatePayrollDTO.getBonus());
        payroll.setDeductions(updatePayrollDTO.getDeductions() == null ? payroll.getDeductions() : updatePayrollDTO.getDeductions());
        payroll.setNetPay(getNetPay(payroll.getUser().getJobRole().getSalary(), updatePayrollDTO.getBonus(), updatePayrollDTO.getDeductions()));
        payroll.setPayDate(updatePayrollDTO.getPayDate());
        payrollRepository.save(payroll);
        return new ApplicationResponseDTO(HttpStatus.CREATED, "PAYROLL_UPDATE_SUCCESSFULLY", "Payroll Update Successfully");
    }

    public ApplicationResponseDTO deletePayroll(Long id) {
        Payroll payroll = payrollRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_PAYROLL_ID", "Invalid Payroll Id"));
        payrollRepository.delete(payroll);
        return new ApplicationResponseDTO(HttpStatus.OK, "PAYROLL_DELETE_SUCCESSFULLY", "Payroll Delete Successfully");
    }

    public Double getNetPay(Double salary, Double bonus, Double deduction) {
        return salary + (bonus == null ? 0.0 : bonus) + (deduction == null ? 0.0 : deduction);
    }

}
