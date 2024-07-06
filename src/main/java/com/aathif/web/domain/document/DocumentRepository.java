package com.aathif.web.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByDocumentType(DocumentType type);
    List<Document> findByEmployeeId(Long employee);
    List<Document> findByEmployeeIdAndDocumentType(Long employee, DocumentType type);
    List<Document> findAllByEmployeeId(Long employeeId);
    List<Document> findAllByEmployeeIdAndDocumentType(Long id, DocumentType type);
}
