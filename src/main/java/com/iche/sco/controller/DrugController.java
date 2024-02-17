package com.iche.sco.controller;

import com.iche.sco.dto.drugs.request.DrugCreationRequest;
import com.iche.sco.dto.drugs.request.DrugDeleteRequest;
import com.iche.sco.dto.drugs.request.DrugsUpdateRequest;
import com.iche.sco.dto.drugs.response.DrugsResponse;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.service.drugServiceImpl.DrugService;
import com.iche.sco.specificationCriteriaSearch.*;
import com.iche.sco.utils.PageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static com.iche.sco.utils.Endpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(DRUG_BASE_URL)
@Slf4j
public class DrugController {
    private final DrugService drugService;
    private final CriteriaSpecificationsService criteriaSpecificationsService;

    @PostMapping(CREATE)
    public ResponseEntity<APIResponse<DrugsResponse>> createDrugs(@RequestBody @Valid DrugCreationRequest drugCreationRequest){
        return new ResponseEntity<>(drugService.createDrug(drugCreationRequest), HttpStatus.CREATED);
    }
    @GetMapping(SINGLE_OF_DRUG)
    public ResponseEntity<APIResponse<DrugsResponse>> getSingleDrug(@PathVariable ("id") Long id){
        return new ResponseEntity<>(drugService.getSingleDrug(id), HttpStatus.OK);
    }
    @GetMapping(LIST_OF_DRUGS)
    public ResponseEntity<APIResponse<PageUtils<DrugsResponse>>> listOfDrugs(
            @RequestParam(value = "pageNo", defaultValue = DrugPageConstant.pageNo) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = DrugPageConstant.pageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = DrugPageConstant.sortBy) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DrugPageConstant.sortDir) String sortDir)
            {
        return new ResponseEntity<>(drugService.listOfDrugs(pageNo,pageSize,sortBy,sortDir), HttpStatus.OK);
    }
    @GetMapping(LIST_OF_DRUGS_MERCHANT)
    public ResponseEntity<APIResponse<PageUtils<DrugsResponse>>> listOfDrugsMerchant(
            @RequestParam(value = "pageNo", defaultValue = DrugPageConstant.pageNo) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = DrugPageConstant.pageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = DrugPageConstant.sortBy) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DrugPageConstant.sortDir) String sortDir,
            @RequestParam(value = "type", defaultValue = "IN_STOCK") String type)
    {
        return new ResponseEntity<>(drugService.listOfDrugsMerchant(pageNo,pageSize,sortBy,sortDir, type), HttpStatus.OK);
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<APIResponse<String>> deleteDrug(@RequestBody DrugDeleteRequest drugDeleteRequest){
        return new ResponseEntity<>(drugService.deleteDrug(drugDeleteRequest), HttpStatus.OK);
    }
    @PutMapping(UPDATE)
    public ResponseEntity<APIResponse<DrugsResponse>> updateDrug(@RequestBody @Valid DrugsUpdateRequest drugsUpdateRequest){
        return new ResponseEntity<>(drugService.updateDrug(drugsUpdateRequest), HttpStatus.OK);
    }
    @PostMapping(SPECIFICATION_SEARCH)
    public ResponseEntity<APIResponse<PageUtils<DrugsResponse>>> drugSpecificationSearch(@RequestBody CriteriaSearchOperation criteriaSearchOperation){
        return new ResponseEntity<>(criteriaSpecificationsService.drugSpecificationSearch(criteriaSearchOperation), HttpStatus.OK);
    }
    @GetMapping("/byName")
    public ResponseEntity<APIResponse<List<DrugsResponse>>> getByName(){
        return new ResponseEntity<>(criteriaSpecificationsService.getDrugsByName(),HttpStatus.OK);
    }
    @PostMapping("/byName")
    public ResponseEntity<APIResponse<List<DrugsResponse>>> byEqual(@RequestBody SinglesSearchSpecification singlesSearchSpecification){
        return new ResponseEntity<>(criteriaSpecificationsService.getDrugsData(singlesSearchSpecification), HttpStatus.OK);
    }
    @PostMapping("/by-date-between")
    public ResponseEntity<APIResponse<PageUtils<DrugsResponse>>> getDrugsBetweenDates(SinglesSearchSpecification singlesSearchSpecification) throws ParseException {
        return new ResponseEntity<>(criteriaSpecificationsService.getDrugsBetweenDates(singlesSearchSpecification),HttpStatus.OK);
    }

    @PostMapping("/byLike")
    public ResponseEntity<APIResponse<PageUtils<DrugsResponse>>> getDrugByLike(SinglesSearchSpecification singlesSearchSpecification) throws ParseException {
        return new ResponseEntity<>(criteriaSpecificationsService.getDrugByLike(singlesSearchSpecification),HttpStatus.OK);
    }

    @PostMapping("/byGreaterThanEqual")
    public ResponseEntity<APIResponse<PageUtils<DrugsResponse>>> getGreaterThan(SinglesSearchSpecification singlesSearchSpecification) {
        return new ResponseEntity<>(criteriaSpecificationsService.getGreaterThan(singlesSearchSpecification),HttpStatus.OK);
    }
    @PostMapping("/byLessThanEqual")
    public ResponseEntity<APIResponse<PageUtils<DrugsResponse>>> getLesserThan(SinglesSearchSpecification searchSpecification) {
        return new ResponseEntity<>(criteriaSpecificationsService.getLessThan(searchSpecification),HttpStatus.OK);
    }
    @PostMapping("/countDelete")
    public ResponseEntity<APIResponse<String>> getCountOfDeletedDrugs(SinglesSearchSpecification singlesSearchSpecification) {
        return new ResponseEntity<>(criteriaSpecificationsService.getCountOfDeletedDrugs(singlesSearchSpecification),HttpStatus.OK);
    }
    @PostMapping("/list-combined-details")
    public ResponseEntity<APIResponse<PageUtils<DrugsResponse>>> drugListSpecificationSearch(@RequestBody SearchRequest searchRequest) {
        log.info("{}", searchRequest);
        return new ResponseEntity<>(criteriaSpecificationsService.drugListSpecificationSearch(searchRequest),HttpStatus.OK);
    }

}
