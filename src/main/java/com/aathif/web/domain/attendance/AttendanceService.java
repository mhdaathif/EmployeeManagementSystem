package com.aathif.web.domain.attendance;

import com.aathif.web.domain.security.model.User;
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
        User user = userService.getCurrentUser();
//        User user = userService.findByUsername(username);
        if (attendanceRepository.findByDate(LocalDate.now()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_ALREADY_ADDED", "Attendance Already Added");
        }
        attendanceRepository.save(
                new Attendance.AttendanceBuilder()
                        .employeeId(user.getId())
                        .checkInTime(LocalTime.now())
                        .date(LocalDate.now())
                        .build()
        );
        return new ApplicationResponseDTO(HttpStatus.CREATED, "ATTENDANCE_ADDES", "Attendance Added!");
    }

    public Boolean isCheckIn() {
//        if (isCheckIn()) {
//            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_ALREADY_CHECKED_ID", "Attendance already checked in!");
//        }
        User user = userService.getCurrentUser();
//        User user = userService.findByUsername(username);
        return attendanceRepository.findByDate(LocalDate.now())
                .map(attendance -> attendance.getEmployeeId().equals(user.getId()) && attendance.getCheckOutTime() == null).orElse(false);
    }

    public List<Attendance> getAttendance() {
//        String username = AuthService.getCurrentUser();
        User user = userService.getCurrentUser();
        return attendanceRepository.findAllByEmployeeId(user.getId());
    }

    public ApplicationResponseDTO checkOut() {
        if (isCheckIn()){
            Optional<Attendance> optionalAttendance = attendanceRepository.findByDate(LocalDate.now());
            if (optionalAttendance.isEmpty()){
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_NOT_FOUND","Attendance not found!");
            }
            Attendance attendance = optionalAttendance.get();
            attendance.setCheckOutTime(LocalTime.now());
            attendanceRepository.save(attendance);
            return new ApplicationResponseDTO(HttpStatus.OK, "ATTENDANCE_CHECKED_OUT", "Attendance Checked Out");
        }
        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_ATTENDANCE", "Invalid Attendance!");
    }

    public Attendance getAttendance(Long id) {
        User user = userService.getCurrentUser();
//        User user = userService.findByUsername(username);
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
        User user = userService.getCurrentUser();
//        User user = userService.findByUsername(username);
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

    public List<Attendance> getAttendanceByUserId(Long userId) {
        return attendanceRepository.findAllByEmployeeId(userId);
    }
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findAlByDate(date);
    }
    public Attendance getAttendanceUserIdAndByDate(Long userId, LocalDate date) {
        return attendanceRepository.findByEmployeeIdAndDate(userId,date);
    }


    //user
    public List<Attendance> getOwnAttendances() {
        User user = userService.findByUsername(userService.getCurrentUser().getUsername());
        return attendanceRepository.findAllByEmployeeId(user.getId());
    }


    public Attendance getOwnAttendanceById(Long id) {
        User user = userService.findByUsername(userService.getCurrentUser().getUsername());
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);

        return getAttendance(user, optionalAttendance);
    }

    public Attendance getOwnAttendanceByDate(LocalDate date) {
        User user = userService.findByUsername(userService.getCurrentUser().getUsername());
        Optional<Attendance> optionalAttendance = attendanceRepository.findByDate(date);
        return getAttendance(user, optionalAttendance);
    }
    private Attendance getAttendance(User user, Optional<Attendance> optionalAttendance) {
        if (optionalAttendance.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "ATTENDANCE_NOT_FOUND", "Attendance not found");
        }
        Attendance attendance = optionalAttendance.get();
        if (!attendance.getEmployeeId().equals(user.getId())) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_ATTENDANCE", "Invalid attendance");
        }
        return attendance;
    }

}
