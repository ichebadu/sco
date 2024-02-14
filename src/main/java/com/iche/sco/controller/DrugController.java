package com.iche.sco.controller;

import com.iche.sco.dto.drugs.request.DrugCreationRequest;
import com.iche.sco.dto.drugs.request.DrugDeleteRequest;
import com.iche.sco.search.specificationCriteriaSearch.SearchSpecification;
import com.iche.sco.search.specificationCriteriaSearch.SpecificationInput;
import com.iche.sco.dto.drugs.request.DrugsUpdateRequest;
import com.iche.sco.dto.drugs.response.DrugsResponse;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.search.specificationCriteriaSearch.DrugPageConstant;
import com.iche.sco.search.criteriaSearch.PageConstant;
import com.iche.sco.search.specificationCriteriaSearch.CriteriaSpecificationsService;
import com.iche.sco.service.drugServiceImpl.DrugService;
import com.iche.sco.search.criteriaSearch.DrugCriteriaSearch;
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
            @RequestParam(value = "pageNo", defaultValue = PageConstant.pageNo) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.pageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstant.sortBy) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.sortDir) String sortDir)
            {
        return new ResponseEntity<>(drugService.listOfDrugs(pageNo,pageSize,sortBy,sortDir), HttpStatus.OK);
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<APIResponse<String>> deleteDrug(@RequestBody DrugDeleteRequest drugDeleteRequest){
        return new ResponseEntity<>(drugService.deleteDrug(drugDeleteRequest), HttpStatus.OK);
    }
    @PutMapping(UPDATE)
    public ResponseEntity<APIResponse<DrugsResponse>> updateDrug(@RequestBody @Valid DrugsUpdateRequest drugsUpdateRequest){
        return new ResponseEntity<>(drugService.updateDrug(drugsUpdateRequest), HttpStatus.OK);
    }
    @PostMapping(DRUG_SEARCH)
    public ResponseEntity<APIResponse<List<DrugsResponse>>> drugSearch(DrugPageConstant pageConstant, DrugCriteriaSearch drugCriteriaSearch){
        return new ResponseEntity<>(drugService.drugSearch(pageConstant, drugCriteriaSearch),HttpStatus.OK);
    }
    @PostMapping(SPECIFICATION_SEARCH)
    public ResponseEntity<APIResponse<List<DrugsResponse>>> drugSpecificationSearch(@RequestBody SpecificationInput specificationInput){
        return new ResponseEntity<>(drugService.drugSpecificationSearch(specificationInput), HttpStatus.OK);
    }
    @GetMapping("/byName")
    List<DrugsResponse> getByName(){
        return criteriaSpecificationsService.getDrugsByName();
    }
    @PostMapping("/byName")
    List<DrugsResponse> byEqual(@RequestBody SearchSpecification searchSpecification){
        return criteriaSpecificationsService.getDrugsData(searchSpecification);
    }
    @PostMapping("/by-date-between")
    public PageUtils<DrugsResponse> getDrugsBetweenDates(SearchSpecification searchSpecification) throws ParseException {
        return criteriaSpecificationsService.getDrugsBetweenDates(searchSpecification);
    }

    @PostMapping("/byLike")
    public PageUtils<DrugsResponse> getDrugByLike(SearchSpecification searchSpecification) throws ParseException {
        return criteriaSpecificationsService.getDrugByLike(searchSpecification);
    }

    @PostMapping("/byGreaterThanEqual")
    public PageUtils<DrugsResponse> getGreaterThan(SearchSpecification searchSpecification) {
        return criteriaSpecificationsService.getGreaterThan(searchSpecification);
    }
    @PostMapping("/byLessThanEqual")
    public PageUtils<DrugsResponse> getLesserThan(SearchSpecification searchSpecification) {
        return criteriaSpecificationsService.getLessThan(searchSpecification);
    }
}
