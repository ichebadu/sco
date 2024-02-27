package com.iche.sco.service.documentService;

import com.iche.sco.model.Drugs;
import com.iche.sco.respository.DrugRepository;
import com.iche.sco.utils.ExcelUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService{
    @Value("${cod.file.storage.location}")
    String fileStorageLocation;
    private final DrugRepository drugRepository;

    @Override
    public ByteArrayInputStream getDataLoaded() {
        List<Drugs> drugsList = drugRepository.findAll();
        ByteArrayInputStream data = ExcelUtils.dataToExcel(drugsList);
        return data;
    }

    @Override
    public String fileUpload(MultipartFile file) {
        String messageResponse = null;

        if (file.isEmpty()) {
            messageResponse = "please select a valid file to upload";
            return messageResponse;
        }
        log.info("u pload file {} ", file.getOriginalFilename());

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(fileStorageLocation + file.getOriginalFilename());
            Files.write(path, bytes);

            messageResponse = "You successfully uploaded '" + file.getOriginalFilename() + "'";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageResponse;
    }

    @Override
    public InputStreamResource downloadFile(String filename){
        log.info("Downloading file {} ", filename);
        InputStreamResource resource = null;
         try{
             File file = new File(fileStorageLocation + filename);
             resource = new InputStreamResource(new FileInputStream(file));
         } catch (FileNotFoundException e){
             e.printStackTrace();
         }
         return resource;
    }
}
