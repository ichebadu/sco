package com.iche.sco.controller;

import com.iche.sco.service.documentService.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

import static com.iche.sco.utils.Endpoint.DRUG_BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(DRUG_BASE_URL)
@Slf4j
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(){
        String fileName = "drugs.xlsx";
        ByteArrayInputStream inputStream = documentService.getDataLoaded();
        InputStreamResource response =  new InputStreamResource(inputStream);

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms.excel")).body(response);
        return responseEntity;
    }
}
