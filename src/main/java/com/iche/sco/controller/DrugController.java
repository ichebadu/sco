package com.iche.sco.controller;

import com.iche.sco.dto.request.DrugCreationRequest;
import com.iche.sco.dto.request.DrugsUpdateRequest;
import com.iche.sco.dto.response.ApiResponse;
import com.iche.sco.dto.response.DrugsResponse;
import com.iche.sco.service.drugServiceImpl.DrugService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.iche.sco.utils.Endpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(DRUG_BASE_URL)
public class DrugController {

    private final DrugService drugService;

    @PostMapping(CREATE)
    public ResponseEntity<ApiResponse<DrugsResponse>> createDrugs(@RequestBody @Valid DrugCreationRequest drugCreationRequest){
        return new ResponseEntity<>(drugService.createDrug(drugCreationRequest), HttpStatus.CREATED);
    }
    @GetMapping(SINGLE_OF_DRUG)
    public ResponseEntity<ApiResponse<DrugsResponse>> getSingleDrug(@PathVariable ("id") Long id){
        return new ResponseEntity<>(drugService.getSingleDrug(id), HttpStatus.OK);
    }
    @GetMapping(LIST_OF_DRUGS)
    public ResponseEntity<ApiResponse<List<DrugsResponse>>> listOfDrugs(){
        return new ResponseEntity<>(drugService.listOfDrugs(), HttpStatus.OK);
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<ApiResponse<String>> deleteDrug(@PathVariable ("id") Long id){
        return new ResponseEntity<>(drugService.deleteDrug(id), HttpStatus.OK);
    }
    @PutMapping(UPDATE)
    public ResponseEntity<ApiResponse<DrugsResponse>> updateDrug(@RequestBody @Valid DrugsUpdateRequest drugsUpdateRequest){
        return new ResponseEntity<>(drugService.updateDrug(drugsUpdateRequest), HttpStatus.OK);
    }

}
