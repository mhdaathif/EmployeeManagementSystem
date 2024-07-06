package com.aathif.web.domain.compliance;

import com.aathif.web.domain.security.model.User;
import com.aathif.web.domain.security.repos.UserRepository;
import com.aathif.web.domain.user.UserService;
import com.aathif.web.dto.ApplicationResponseDTO;
import com.aathif.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ApplicationResponseDTO raiseCompliant(ComplaintDTO complaintDTO) {
        User user = userService.getCurrentUser();
        complaintRepository.save(
                Complaint.builder()
                        .employeeId(user.getId())
                        .description(complaintDTO.getDescription())
                        .complianceType(complaintDTO.getComplaintType())
                        .complaintStatus(ComplaintStatus.RAISED)
                        .build()
        );
        return new ApplicationResponseDTO(HttpStatus.CREATED, "COMPLAINT_CREATE_SUCCESSFULLY", "Complaint Create Successfully");
    }

    public List<Complaint> getComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaint(Long id) {
        return complaintRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_COMPLAINT_ID", "Invalid Complaint Id"));
    }

    public List<Complaint> getComplaints(Long userId) {
        return complaintRepository.findAllByEmployeeId(userId);
    }

    public List<Complaint> getComplaints(ComplaintType complaintType) {
        return complaintRepository.findAllByComplianceType(complaintType);
    }

    public List<Complaint> getComplaints(Long userId, ComplaintType complaintType) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_EMPLOYEE_ID", "Invalid Employee Id"));
        return complaintRepository.findAllByEmployeeIdAndComplianceType(user.getId(), complaintType);
    }

    public List<Complaint> getComplaints(ComplaintStatus complaintStatus) {
        return complaintRepository.findAllByComplaintStatus(complaintStatus);
    }

    public List<Complaint> getComplaints(Long userId, ComplaintStatus complaintStatus) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_EMPLOYEE_ID", "Invalid Employee Id"));
        return complaintRepository.findAllByEmployeeIdAndComplaintStatus(user.getId(), complaintStatus);
    }

    public List<Complaint> getOwnComplaints() {
        User user = userService.getCurrentUser();
        return complaintRepository.findAllByEmployeeId(user.getId());
    }

    public Complaint getOwnComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_COMPLAINT_ID", "Invalid Complaint Id"));
        User user = userService.getCurrentUser();
        if (!complaint.getEmployeeId().equals(user.getId()))
            throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "UNAUTHORIZED_COMPLAINT", "You are not authorized to access this complaint!");
        return complaint;
    }

    public List<Complaint> getOwnComplaints(ComplaintType complaintType) {
        User user = userService.getCurrentUser();
        return complaintRepository.findAllByEmployeeIdAndComplianceType(user.getId(), complaintType);
    }

    public List<Complaint> getOwnComplaints(ComplaintStatus complaintStatus) {
        User user = userService.getCurrentUser();
        return complaintRepository.findAllByEmployeeIdAndComplaintStatus(user.getId(), complaintStatus);
    }

//    public ApplicationResponseDTO updateComplaint(Long id, ComplaintDTO complaintDTO) {
//        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_COMPLAINT_ID", "Invalid Complaint Id"));
//        complaint.setDescription(complaintDTO.getDescription());
//        complaint.setComplianceType(complaintDTO.getComplaintType());
//        complaintRepository.save(complaint);
//        return new ApplicationResponseDTO(HttpStatus.CREATED, "COMPLAINT_UPDATE_SUCCESSFULLY", "Complaint Update Successfully");
//    }

    public ApplicationResponseDTO updateToInProgress(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_COMPLAINT_ID", "Invalid Complaint Id"));
        if (complaint.getComplaintStatus().equals(ComplaintStatus.RAISED)) {
            complaint.setComplaintStatus(ComplaintStatus.IN_PROGRESS);
            complaintRepository.save(complaint);
            return new ApplicationResponseDTO(HttpStatus.OK, "COMPLAINT_IN_PROGRESS", "Complaint In Progress");
        }
        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "COMPLAINT_ALREADY_" + complaint.getComplaintStatus(), "Complaint Already" + complaint.getComplaintStatus());
    }

    public ApplicationResponseDTO updateToResolved(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_COMPLAINT_ID", "Invalid Complaint Id"));
        if (complaint.getComplaintStatus().equals(ComplaintStatus.IN_PROGRESS)) {
            complaint.setComplaintStatus(ComplaintStatus.RESOLVED);
            complaintRepository.save(complaint);
            return new ApplicationResponseDTO(HttpStatus.OK, "COMPLAINT_IN_PROGRESS", "Complaint In Progress");
        }
        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "COMPLAINT_ALREADY_" + complaint.getComplaintStatus(), "Complaint Already" + complaint.getComplaintStatus());
    }

}
