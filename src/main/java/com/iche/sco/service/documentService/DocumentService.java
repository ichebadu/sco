package com.iche.sco.service.documentService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

public interface DocumentService {
    ByteArrayInputStream getDataLoaded();

    String fileUpload(MultipartFile file);

    InputStreamResource downloadFile(String filename);
}
