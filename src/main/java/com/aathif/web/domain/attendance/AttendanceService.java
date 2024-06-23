package com.aathif.web.domain.attendance;

import com.aathif.web.domain.security.model.User;
import com.aathif.web.domain.security.service.AuthService;
import com.aathif.web.domain.user.UserService;
import com.aathif.web.dto.ApplicationResponseDTO;
import com.aathif.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AttendanceService {
    public final AttendanceRepository attendanceRepository;
    private final UserService userService;

    public ApplicationResponseDTO checkIn() {
        String username = AuthService.getCurrentUser();
        User user = userService.findByUsername(username);
        if (attendanceRepository.findByDate(LocalDate.now()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_ALREADY_ADDED", "Attendance Already Added");
        }
        attendanceRepository.save(
                new Attendance.AttendanceBuilder()
                        .employeeId(user.getId())
                        .checkInTime(LocalTime.now())
                        .build()
        );
        return new ApplicationResponseDTO(HttpStatus.CREATED, "ATTENDANCE_ADDES", "Attendance Added!");
    }

    public List<Attendance> getAttendance() {
        String username = AuthService.getCurrentUser();
        User user = userService.findByUsername(username);
        return attendanceRepository.findAllByEmployeeId(user.getId());
    }

    public ApplicationResponseDTO checkOut(Long id) {
        if (attendanceRepository.findByDate(LocalDate.now()).isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_NOT_ADDED", "Attendance not added!");
        }
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_NOT_FOUND", "Attendance Not Found"));
        if (attendance.getCheckOutTime() != null) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_ALREADY_CHECKED_OUT", "Attendance Already Checked Out");
        }
        attendance.setCheckOutTime(LocalTime.now());
        attendanceRepository.save(attendance);
        return new ApplicationResponseDTO(HttpStatus.OK, "ATTENDANCE_CHECKED_OUT", "Attendance Checked Out");
    }

    public Attendance getAttendance(Long id) {
        String username = AuthService.getCurrentUser();
        User user = userService.findByUsername(username);
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);
        if (optionalAttendance.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_NOT_FOUND", "Attendance Not Found");
        }
        Attendance attendance = optionalAttendance.get();
        if (!attendance.getEmployeeId().equals(user.getId())) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_ATTENDANCE", "Invalid Attendance!");
        }
        return attendance;
    }

    public Attendance getAttendance(LocalDate date) {
        String username = AuthService.getCurrentUser();
        User user = userService.findByUsername(username);
        Optional<Attendance> optionalAttendance = attendanceRepository.findByDate(date);
        if (optionalAttendance.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_NOT_FOUND", "Attendance Not Found");
        }
        Attendance attendance = optionalAttendance.get();
        if (!attendance.getEmployeeId().equals(user.getId())) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_ATTENDANCE", "Invalid Attendance!");
        }
        return attendance;
    }

}
