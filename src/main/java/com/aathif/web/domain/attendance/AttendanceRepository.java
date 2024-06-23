package com.aathif.web.domain.attendance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByDate(LocalDate now);
    List<Attendance> findAllByEmployeeId(Long id);
}
