package com.aathif.web.domain.document;

import com.aathif.web.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/document")
public class DocumentResource {
    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<ApplicationResponseDTO> uploadDocument(@RequestBody DocumentDTO documentDTO) {
        return ResponseEntity.ok(documentService.uploadDocument(documentDTO));
    }

    @PostMapping("/upload-file/{id}")
    public ResponseEntity<ApplicationResponseDTO> uploadDocumentFile(@PathVariable("id") Long id, @RequestBody MultipartFile file) {
        return ResponseEntity.ok(documentService.uploadDocumentFile(id, file));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateDocument(@PathVariable("id") Long id, @RequestBody DocumentUpdateDTO documentUpdateDTO) {
        return ResponseEntity.ok(documentService.updateDocument(id, documentUpdateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApplicationResponseDTO> deleteDocument(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentService.deleteDocument(id));
    }

    //admin
    @GetMapping("/get")
    public ResponseEntity<List<Document>> getDocuments() {
        return ResponseEntity.ok(documentService.getDocuments());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentService.getDocument(id));
    }

    @GetMapping("/get/type/{type}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable("type") DocumentType type) {
        return ResponseEntity.ok(documentService.getDocuments(type));
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable("userId") Long employeeId) {
        return ResponseEntity.ok(documentService.getDocuments(employeeId));
    }

    @GetMapping("/get/type/{userId}/{type}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable("userId") Long id, @PathVariable("type") DocumentType type) {
        return ResponseEntity.ok(documentService.getDocuments(id, type));
    }

    //Own
    @GetMapping("/get-own")
    public ResponseEntity<List<Document>> getOwnDocuments() {
        return ResponseEntity.ok(documentService.getOwnDocuments());
    }

    @GetMapping("/get-own/{id}")
    public ResponseEntity<Document> getOwnDocument(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentService.getOwnDocument(id));
    }

    @GetMapping("/get-own/type/{type}")
    public ResponseEntity<List<Document>> getOwnDocuments(@PathVariable("type") DocumentType type) {
        return ResponseEntity.ok(documentService.getOwnDocuments(type));
    }

}
