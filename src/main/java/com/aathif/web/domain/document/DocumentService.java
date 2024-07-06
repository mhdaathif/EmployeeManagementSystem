package com.aathif.web.domain.document;

import com.aathif.web.domain.security.model.User;
import com.aathif.web.domain.security.repos.UserRepository;
import com.aathif.web.domain.user.UserService;
import com.aathif.web.dto.ApplicationResponseDTO;
import com.aathif.web.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ApplicationResponseDTO uploadDocument(DocumentDTO documentDTO) {
        userRepository.findById(documentDTO.getEmployeeId()).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "INVALID_USER_ID", "Invalid User Id"));
        documentRepository.save(
                Document.builder()
                        .employeeId(documentDTO.getEmployeeId())
                        .title(documentDTO.getTitle())
                        .documentType(documentDTO.getDocumentType())
                        .build()
        );
        return new ApplicationResponseDTO(HttpStatus.OK, "DOCUMENT_UPLOADED_SUCCESSFULLY", "Document Uploaded Successfully");
    }

    public ApplicationResponseDTO uploadDocumentFile(Long id, MultipartFile file) {

        documentRepository.findById(id).orElseThrow(() ->
                new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_DOCUMENT_ID", "Invalid document id")
        );

        if (file.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "FILE_NOT_SELECTED", "File not Selected");
        } else {
            try {
                String projectRoot = System.getProperty("user.dir");
                String originalFilename = file.getOriginalFilename();
                if (originalFilename != null) {
                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

                    if (!(fileExtension.equalsIgnoreCase(".zip") || fileExtension.equalsIgnoreCase(".pdf"))) {
                        throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_FILE_TYPE", "Invalid file type. Only zip and pdf are allowed.");
                    }

                    String newFileName = UUID.randomUUID() + fileExtension;
                    String imagePath = "/uploads/documents/" + newFileName;
                    Path path = Paths.get(projectRoot + imagePath);
                    File saveFile = new File(String.valueOf(path));
                    file.transferTo(saveFile);

                    Document document = documentRepository.findById(id).get();
                    document.setDocumentUrl(newFileName);
                    documentRepository.save(document);
                    return new ApplicationResponseDTO(HttpStatus.CREATED, "FILE_UPLOADED_SUCCESSFULLY", "File Uploaded Successfully!");
                } else {
                    throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "ORIGINAL_FILE_NAME_NOT_FOUND", "Original File Name Not Found");
                }
            } catch (IOException e) {
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "FILE_NOT_SAVED", "File not Saved");
            }
        }

    }

    public ApplicationResponseDTO updateDocument(Long id, DocumentUpdateDTO documentUpdateDTO) {
        Document document = documentRepository.findById(id).orElseThrow(() ->
                new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_DOCUMENT_ID", "Invalid document id")
        );

        document.setTitle(documentUpdateDTO.getTitle());
        document.setDocumentType(documentUpdateDTO.getDocumentType());
        documentRepository.save(document);
        return new ApplicationResponseDTO(HttpStatus.CREATED, "DOCUMENT_UPDATE_SUCCESSFULLY", "Document Update Successfully");

    }


    public ApplicationResponseDTO deleteDocument(Long id) {
        Document document = documentRepository.findById(id).orElseThrow(() ->
                new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_DOCUMENT_ID", "Invalid document id")
        );
        documentRepository.delete(document);
        return new ApplicationResponseDTO(HttpStatus.OK, "DOCUMENT_DELETE_SUCCESSFULLY", "Document Delete Successfully");
    }

    //Admin
    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }


    public Document getDocument(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_DOCUMENT_ID", "Invalid Document Id"));
    }

    public List<Document> getDocuments(Long employeeId) {
        userRepository.findById(employeeId).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "EMPLOYEE_ID_NOT_FOUND", "Employee Id Not Found"));
        return documentRepository.findByEmployeeId(employeeId);
    }

    public List<Document> getDocuments(Long employeeId, DocumentType documentType) {
        User user = userRepository.findById(employeeId).orElseThrow(() ->
                new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_USER_ID", "Invalid user id")
        );
        return documentRepository.findByEmployeeIdAndDocumentType(user.getId(), documentType);
    }

    public List<Document> getDocuments(DocumentType type) {
        return documentRepository.findAllByDocumentType(type);
    }

    // Own
    public List<Document> getOwnDocuments() {
        User user = userService.getCurrentUser();
        return documentRepository.findByEmployeeId(user.getId());
    }

    public Document getOwnDocument(Long id) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_DOCUMENT_ID", "Invalid Document Id"));
        User user = userService.getCurrentUser();
        if (document.getEmployeeId().equals(user.getId())) {
            return document;
        }
        throw new ApplicationCustomException(HttpStatus.FORBIDDEN, "UNAUTHORIZED_DOCUMENNT", "Un Authorized Document");
    }

    public List<Document> getOwnDocuments(DocumentType type) {
        User user = userService.getCurrentUser();
        return documentRepository.findAllByEmployeeIdAndDocumentType(user.getId(), type);
    }

}
