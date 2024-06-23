package com.aathif.web.domain.attendance;

import com.aathif.web.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/attendance")
public class AttendanceResource {
    private final AttendanceService attendanceService;

    @PostMapping("/add")
    public ResponseEntity<ApplicationResponseDTO> addAttendance() {
        return ResponseEntity.ok(attendanceService.checkIn());
    }

    @GetMapping("/get")
    public ResponseEntity<List<Attendance>> getAttendances() {
        return ResponseEntity.ok(attendanceService.getAttendance());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> checkOutAttendance(@PathVariable("id") Long id) {
        return ResponseEntity.ok(attendanceService.checkOut(id));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Attendance> getAttendances(@PathVariable("id") Long id) {
        return ResponseEntity.ok(attendanceService.getAttendance(id));
    }

    @GetMapping("/get-by-date/{date}")
    public ResponseEntity<Attendance> getAttendances(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendance(date));
    }

}
