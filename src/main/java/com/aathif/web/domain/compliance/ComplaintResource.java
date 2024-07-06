package com.aathif.web.domain.compliance;

import com.aathif.web.dto.ApplicationResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/complaint")
public class ComplaintResource {
    private final ComplaintService complaintService;

    @PostMapping("/raise")
    public ResponseEntity<ApplicationResponseDTO> raiseCompliant(@Valid @RequestBody ComplaintDTO complaintDTO) {
        return ResponseEntity.ok(complaintService.raiseCompliant(complaintDTO));
    }

    @PutMapping("/update-to-inprogress/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateToInProgress(@PathVariable("id") Long id) {
        return ResponseEntity.ok(complaintService.updateToInProgress(id));
    }

    @PutMapping("/update-to-resolved/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateToResolved(@PathVariable("id") Long id) {
        return ResponseEntity.ok(complaintService.updateToResolved(id));
    }

    @GetMapping("/get")
    public ResponseEntity<List<Complaint>> getComplaints() {
        return ResponseEntity.ok(complaintService.getComplaints());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Complaint> getComplaint(@PathVariable("id") Long id) {
        return ResponseEntity.ok(complaintService.getComplaint(id));
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(complaintService.getComplaints(userId));
    }

    @GetMapping("/get/type/{type}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("type") ComplaintType complaintType) {
        return ResponseEntity.ok(complaintService.getComplaints(complaintType));
    }

    @GetMapping("/get/type/{id}/{type}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("id") Long userId, @PathVariable("type") ComplaintType complaintType) {
        return ResponseEntity.ok(complaintService.getComplaints(userId, complaintType));
    }

    @GetMapping("/get/status/{status}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("status") ComplaintStatus complaintStatus) {
        return ResponseEntity.ok(complaintService.getComplaints(complaintStatus));
    }

    @GetMapping("/get/status/{id}/{status}")
    public ResponseEntity<List<Complaint>> getComplaints(@PathVariable("id") Long userId, @PathVariable("status") ComplaintStatus complaintStatus) {
        return ResponseEntity.ok(complaintService.getComplaints(userId, complaintStatus));
    }

    //Own
    @GetMapping("/get-own")
    public ResponseEntity<List<Complaint>> getOwnComplaints() {
        return ResponseEntity.ok(complaintService.getOwnComplaints());
    }

    @GetMapping("/get-own/{id}")
    public ResponseEntity<Complaint> getOwnComplaint(@PathVariable("id") Long id) {
        return ResponseEntity.ok(complaintService.getOwnComplaint(id));
    }

    @GetMapping("/get-own/type/{type}")
    public ResponseEntity<List<Complaint>> getOwnComplaints(@PathVariable("type") ComplaintType complaintType) {
        return ResponseEntity.ok(complaintService.getOwnComplaints(complaintType));
    }

    @GetMapping("/get-own/status/{status}")
    public ResponseEntity<List<Complaint>> getOwnComplaints(@PathVariable("status") ComplaintStatus complaintStatus) {
        return ResponseEntity.ok(complaintService.getOwnComplaints(complaintStatus));
    }



}
