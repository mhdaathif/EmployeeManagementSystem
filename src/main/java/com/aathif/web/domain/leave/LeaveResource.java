package com.aathif.web.domain.leave;

import com.aathif.web.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/leave")
public class LeaveResource {
    private final LeaveService leaveService;

    @PostMapping("/request")
    public ResponseEntity<ApplicationResponseDTO> addLeave(@RequestBody LeaveDTO leaveDTO) {
        return ResponseEntity.ok(leaveService.addLeave(leaveDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Leave>> getLeave() {
        return ResponseEntity.ok(leaveService.getLeaves());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Leave> getLeaveById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(leaveService.getLeaveById(id));
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<Leave>> getLeaveByUserId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(leaveService.getLeaveByUserId(id));
    }

    @GetMapping("/get/type/{type}")
    public ResponseEntity<List<Leave>> getLeaveByLeaveType(@PathVariable("type") LeaveType leaveType) {
        return ResponseEntity.ok(leaveService.getLeaveByLeaveType(leaveType));
    }

    @GetMapping("/get/type/{id}/{type}")
    public ResponseEntity<List<Leave>> getUserLeavesByLeaveType(@PathVariable("id") Long id, @PathVariable("type") LeaveType leaveType) {
        return ResponseEntity.ok(leaveService.getUserLeavesByLeaveType(id, leaveType));
    }

    @GetMapping("/get/status/{status}")
    public ResponseEntity<List<Leave>> getLeaveByLeaveStatus(@PathVariable("status") LeaveStatus leaveStatus) {
        return ResponseEntity.ok(leaveService.getLeaveByLeaveStatus(leaveStatus));
    }

    @GetMapping("/get/status/{id}/{status}")
    public ResponseEntity<List<Leave>> getUserLeaveByLeaveStatus(@PathVariable("id") Long id, @PathVariable("status") LeaveStatus leaveStatus) {
        return ResponseEntity.ok(leaveService.getUserLeaveByLeaveStatus(id, leaveStatus));
    }

    @GetMapping("/get-own")
    public ResponseEntity<List<Leave>> getOwnLeaves() {
        return ResponseEntity.ok(leaveService.getOwnLeaves());
    }

    @GetMapping("/get-own/{id}")
    public ResponseEntity<Leave> getOwnLeaveById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(leaveService.getOwnLeaveById(id));
    }

    @GetMapping("/get-own/type/{type}")
    public ResponseEntity<List<Leave>> getOwnLeaveByLeaveType(@PathVariable("type") LeaveType leaveType) {
        return ResponseEntity.ok(leaveService.getOwnLeaveByLeaveType(leaveType));
    }

    @GetMapping("/get-own/status/{status}")
    public ResponseEntity<List<Leave>> getOwnLeaveByLeaveStatus(@PathVariable("status") LeaveStatus leaveStatus) {
        return ResponseEntity.ok(leaveService.getOwnLeaveByLeaveStatus(leaveStatus));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateLeave(@PathVariable("id") Long id, @RequestBody LeaveDTO leaveDTO){
        return ResponseEntity.ok(leaveService.updateLeave(id, leaveDTO));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<ApplicationResponseDTO> cancelLeave(@PathVariable("id") Long id){
        return ResponseEntity.ok(leaveService.cancelLeave(id));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApplicationResponseDTO> approveLeave(@PathVariable("id") Long id){
        return ResponseEntity.ok(leaveService.approveLeave(id));
    }

}
