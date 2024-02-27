package com.iche.sco.controller;

import com.iche.sco.service.documentService.DocumentService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

import static com.iche.sco.utils.Endpoint.DRUG_BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(DRUG_BASE_URL)
@Slf4j
public class DocumentController {
    private final DocumentService documentService;
    private final ServletContext context;

    @GetMapping("/document/download")
    public ResponseEntity<InputStreamResource> download(){
        String fileName = "drugs.xlsx";
        ByteArrayInputStream inputStream = documentService.getDataLoaded();
        InputStreamResource response =  new InputStreamResource(inputStream);


        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms.excel")).body(response);
        return responseEntity;
    }

    @PostMapping("/document/file-upload")
    public String uploadFile(@RequestParam("file")MultipartFile file){
        log.info("upload File {} ", file.getOriginalFilename());
        return  documentService.fileUpload(file);
    }
    @GetMapping("/document/file-download/{filename}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable ("filename") String filename){
        log.info("Downloading file {}", filename);
        InputStreamResource resource = documentService.downloadFile(filename);
        return ResponseEntity.ok()
                .contentType(getMediaTypeForFileName(context, filename))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+filename)
                .body(resource);
    }
    private MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName){
        String mimetype = servletContext.getMimeType(fileName);
        try{
            MediaType mediaType = MediaType.parseMediaType(mimetype);
            return mediaType;
        } catch (Exception e){
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
