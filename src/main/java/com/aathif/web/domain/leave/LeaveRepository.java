package com.aathif.web.domain.leave;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findAllByEmployeeId(Long userId);
    List<Leave> findAllByLeaveType(LeaveType leaveType);
    List<Leave> findAllByEmployeeIdAndLeaveType(Long userId, LeaveType leaveType);
    List<Leave> findAllByEmployeeIdAndLeaveStatus(Long userId, LeaveStatus leaveStatus);
    List<Leave> findAllByLeaveStatus(LeaveStatus leaveStatus);
}
