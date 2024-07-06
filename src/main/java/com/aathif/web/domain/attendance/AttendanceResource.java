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

    @PostMapping("/check-in")
    public ResponseEntity<ApplicationResponseDTO> checkIn() {
        return ResponseEntity.ok(attendanceService.checkIn());
    }

    @GetMapping("/is-check-in")
    public ResponseEntity<Boolean> isCheckIn(){
        return ResponseEntity.ok(attendanceService.isCheckIn());
    }

    @GetMapping("/get")
    public ResponseEntity<List<Attendance>> getAttendances() {
        return ResponseEntity.ok(attendanceService.getAttendance());
    }

    @PutMapping("/check-out")
    public ResponseEntity<ApplicationResponseDTO> checkOut() {
        return ResponseEntity.ok(attendanceService.checkOut());
    }

    // Admin
    @GetMapping("/get/{id}")
    public ResponseEntity<Attendance> getAttendances(@PathVariable("id") Long id) {
        return ResponseEntity.ok(attendanceService.getAttendance(id));
    }

    @GetMapping("/get/date/{date}")
    public ResponseEntity<Attendance> getAttendances(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendance(date));
    }

    @GetMapping("/get/user/{user-id}")
    public ResponseEntity<List<Attendance>> getAttendanceByUserId(@PathVariable("user-id") Long userId){
        return ResponseEntity.ok(attendanceService.getAttendanceByUserId(userId));
    }
    @GetMapping("/get/user/{user-id}/{date}")
    public ResponseEntity<Attendance> getAttendanceUserIdAndByDate(@PathVariable("user-id") Long userId, @PathVariable("date") LocalDate date){
        return ResponseEntity.ok(attendanceService.getAttendanceUserIdAndByDate(userId, date));
    }


    //user
    @GetMapping("/get-own")
    public ResponseEntity<List<Attendance>> getOwnAttendances(){
        return ResponseEntity.ok(attendanceService.getOwnAttendances());
    }
    @GetMapping("/get-own/{id}")
    public ResponseEntity<Attendance> getOwnAttendanceById(@PathVariable("id") Long Id){
        return ResponseEntity.ok(attendanceService.getOwnAttendanceById(Id));
    }
    @GetMapping("/get-own/date/{date}")
    public ResponseEntity<Attendance> getOwnAttendanceByDate(@PathVariable("date") LocalDate date){
        return ResponseEntity.ok(attendanceService.getOwnAttendanceByDate(date));
    }

}
