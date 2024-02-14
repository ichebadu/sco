package com.iche.sco.service.drugServiceImpl;

import com.iche.sco.dto.drugs.request.DrugCreationRequest;
import com.iche.sco.dto.drugs.request.DrugDeleteRequest;
import com.iche.sco.search.specificationCriteriaSearch.SpecificationInput;
import com.iche.sco.dto.drugs.request.DrugsUpdateRequest;
import com.iche.sco.dto.drugs.response.DrugsResponse;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.search.specificationCriteriaSearch.DrugPageConstant;
import com.iche.sco.search.criteriaSearch.DrugCriteriaSearch;
import com.iche.sco.utils.PageUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DrugService {

    APIResponse<DrugsResponse> createDrug(DrugCreationRequest drugCreationRequest);

    APIResponse<DrugsResponse> getSingleDrug(Long id);

    APIResponse<PageUtils<DrugsResponse>> listOfDrugs(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    APIResponse<String> deleteDrug(DrugDeleteRequest drugDeleteRequest);

    APIResponse<DrugsResponse>updateDrug(DrugsUpdateRequest drugsUpdateRequest);

    APIResponse<List<DrugsResponse>> drugSearch(DrugPageConstant pageConstant, DrugCriteriaSearch drugCriteriaSearch);

    @Transactional(readOnly = true)
    APIResponse<List<DrugsResponse>> drugSpecificationSearch(SpecificationInput specificationInput);
}
