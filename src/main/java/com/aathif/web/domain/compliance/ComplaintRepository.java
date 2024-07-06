package com.aathif.web.domain.compliance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findAllByEmployeeId(Long userId);
    List<Complaint> findAllByComplianceType(ComplaintType complaintType);
    List<Complaint> findAllByEmployeeIdAndComplianceType(Long userId, ComplaintType complaintType);
    List<Complaint> findAllByComplaintStatus(ComplaintStatus complaintStatus);

    List<Complaint> findAllByEmployeeIdAndComplaintStatus(Long id, ComplaintStatus complaintStatus);
}
