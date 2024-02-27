package com.iche.sco.service.documentService;

import com.iche.sco.model.Drugs;
import com.iche.sco.respository.DrugRepository;
import com.iche.sco.utils.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{
    private final DrugRepository drugRepository;

    @Override
    public ByteArrayInputStream getDataLoaded() {
        List<Drugs> drugsList = drugRepository.findAll();
        ByteArrayInputStream data = ExcelUtils.dataToExcel(drugsList);
        return data;
    }
}
