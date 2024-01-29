package com.iche.sco.controller;

import com.iche.sco.dto.drugs.request.DrugCreationRequest;
import com.iche.sco.dto.drugs.request.DrugDeleteRequest;
import com.iche.sco.dto.drugs.request.DrugsUpdateRequest;
import com.iche.sco.dto.drugs.response.DrugsResponse;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.service.drugServiceImpl.DrugService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.iche.sco.utils.Endpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(DRUG_BASE_URL)
@Slf4j
public class DrugController {

    private final DrugService drugService;

    @PostMapping(CREATE)
    public ResponseEntity<APIResponse<DrugsResponse>> createDrugs(@RequestBody @Valid DrugCreationRequest drugCreationRequest){
        return new ResponseEntity<>(drugService.createDrug(drugCreationRequest), HttpStatus.CREATED);
    }
    @GetMapping(SINGLE_OF_DRUG)
    public ResponseEntity<APIResponse<DrugsResponse>> getSingleDrug(@PathVariable ("id") Long id){
        return new ResponseEntity<>(drugService.getSingleDrug(id), HttpStatus.OK);
    }
    @GetMapping(LIST_OF_DRUGS)
    public ResponseEntity<APIResponse<List<DrugsResponse>>> listOfDrugs(){
        return new ResponseEntity<>(drugService.listOfDrugs(), HttpStatus.OK);
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<APIResponse<String>> deleteDrug(@RequestBody DrugDeleteRequest drugDeleteRequest){
        return new ResponseEntity<>(drugService.deleteDrug(drugDeleteRequest), HttpStatus.OK);
    }
    @PutMapping(UPDATE)
    public ResponseEntity<APIResponse<DrugsResponse>> updateDrug(@RequestBody @Valid DrugsUpdateRequest drugsUpdateRequest){
        return new ResponseEntity<>(drugService.updateDrug(drugsUpdateRequest), HttpStatus.OK);
    }

}
