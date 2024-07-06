package com.aathif.web.domain.leave;

import com.aathif.web.domain.security.model.User;
import com.aathif.web.domain.user.UserService;
import com.aathif.web.dto.ApplicationResponseDTO;
import com.aathif.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final UserService userService;

    public ApplicationResponseDTO addLeave(LeaveDTO leaveDTO) {
        if (leaveDTO.getFromDate().isAfter(leaveDTO.getToDate())){
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_LEAVE_DATE", "Leave From Date should be less than Leave To Date");
        }
        User user = userService.getCurrentUser();
        leaveRepository.save(
                Leave.builder()
                        .leaveType(leaveDTO.getLeaveType())
                        .fromDate(leaveDTO.getFromDate())
                        .toDate(leaveDTO.getToDate())
                        .employeeId(user.getId())
                        .leaveStatus(LeaveStatus.PENDING)
                        .build()
        );
        return new ApplicationResponseDTO(HttpStatus.CREATED, "LEAVE_REQUEST_SENT_SUCCESSFULLY", "Leave Request Send Successfully");
    }

    public List<Leave> getLeaves() {
        return leaveRepository.findAll();
    }

    public Leave getLeaveById(Long id) {
        return leaveRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_LEAVE_ID", "Invalid Leave Id"));
    }

    public List<Leave> getLeaveByUserId(Long userId) {
        return leaveRepository.findAllByEmployeeId(userId);
    }

    public List<Leave> getLeaveByLeaveType(LeaveType leaveType) {
        return leaveRepository.findAllByLeaveType(leaveType);
    }

    public List<Leave> getUserLeavesByLeaveType(Long userId, LeaveType leaveType) {
        return leaveRepository.findAllByEmployeeIdAndLeaveType(userId, leaveType);
    }

    public List<Leave> getLeaveByLeaveStatus(LeaveStatus leaveStatus) {
        return leaveRepository.findAllByLeaveStatus(leaveStatus);
    }

    public List<Leave> getUserLeaveByLeaveStatus(Long userId, LeaveStatus leaveStatus) {
        return leaveRepository.findAllByEmployeeIdAndLeaveStatus(userId, leaveStatus);
    }

    public List<Leave> getOwnLeaves() {
        User user = userService.getCurrentUser();
        return leaveRepository.findAllByEmployeeId(user.getId());
    }

    public Leave getOwnLeaveById(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_LEAVE_ID", "Invalid Leave Id"));
        User user = userService.getCurrentUser();
        if (!leave.getEmployeeId().equals(user.getId())) {
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "UNAUTHORIZED_LEAVE", "Unauthorized Leave");
        }
        return leave;
    }

    public List<Leave> getOwnLeaveByLeaveType(LeaveType leaveType) {
        User user = userService.getCurrentUser();
        return leaveRepository.findAllByEmployeeIdAndLeaveType(user.getId(), leaveType);
    }

    public List<Leave> getOwnLeaveByLeaveStatus(LeaveStatus leaveStatus) {
        User user = userService.getCurrentUser();
        return leaveRepository.findAllByEmployeeIdAndLeaveStatus(user.getId(), leaveStatus);
    }

    public ApplicationResponseDTO updateLeave(Long id, LeaveDTO leaveDTO){
        if (leaveDTO.getFromDate().isAfter(leaveDTO.getToDate())){
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_LEAVE_DATE", "Leave From Date should be less than Leave To Date");
        }
        Leave leave = leaveRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_LEAVE_ID", "Invalid Leave Id"));
        if (leave.getLeaveStatus().equals(LeaveStatus.PENDING)){
            leave.setLeaveType(leaveDTO.getLeaveType());
            leave.setFromDate(leaveDTO.getFromDate());
            leave.setToDate(leaveDTO.getToDate());
            leaveRepository.save(leave);
            return new ApplicationResponseDTO(HttpStatus.OK, "LEAVE_UPDATE_SUCCESSFULLY", "Leave Update Successfully");
        }
        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "CANNOT_UPDATE_UN_PENDING_LEAVES", "Can't Update Un Pending Leaves");
    }

    public ApplicationResponseDTO cancelLeave(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow(() ->
                new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_LEAVE_ID", "Invalid leave id")
        );

        if (leave.getLeaveStatus().equals(LeaveStatus.PENDING)){
            leave.setLeaveStatus(LeaveStatus.CANCELLED);
            leaveRepository.save(leave);
            return new ApplicationResponseDTO(HttpStatus.CREATED, "LEAVE_CANCELLED_SUCCESSFULLY", "Leave cancelled successfully");
        } else if (leave.getLeaveStatus().equals(LeaveStatus.CANCELLED)){
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "LEAVE_ALREADY_SUCCESSFULLY", "Leave already successfully");
        }

        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "YOU_CANNOT_CANCEL_APPROVED_OR_REJECTED_LEAVE", "You cannot cancel approved or rejected leave");
    }

    public ApplicationResponseDTO approveLeave(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow(() ->
                new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_LEAVE_ID", "Invalid leave id")
        );

        if (leave.getLeaveStatus().equals(LeaveStatus.PENDING)) {
            leave.setLeaveStatus(LeaveStatus.APPROVED);
            leaveRepository.save(leave);
            return new ApplicationResponseDTO(HttpStatus.CREATED, "LEAVE_APPROVED_SUCCESSFULLY", "Leave approved successfully");
        } else if (leave.getLeaveStatus().equals(LeaveStatus.APPROVED)) {
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "LEAVE_ALREADY_SUCCESSFULLY", "Leave already successfully");
        }

        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "YOU_CANNOT_APPROVE_REJECTED_LEAVE", "You cannot approve rejected leave");
    }

}
