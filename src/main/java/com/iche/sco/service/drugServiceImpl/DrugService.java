package com.iche.sco.service.drugServiceImpl;

import com.iche.sco.dto.drugs.request.DrugCreationRequest;
import com.iche.sco.dto.drugs.request.DrugDeleteRequest;
import com.iche.sco.dto.drugs.request.DrugsUpdateRequest;
import com.iche.sco.dto.drugs.response.DrugsResponse;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.utils.PageUtils;

public interface DrugService {

    APIResponse<DrugsResponse> createDrug(DrugCreationRequest drugCreationRequest);

    APIResponse<DrugsResponse> getSingleDrug(Long id);

    APIResponse<PageUtils<DrugsResponse>> listOfDrugs(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    APIResponse<PageUtils<DrugsResponse>> listOfDrugsMerchant(Integer pageNo, Integer pageSize, String sortBy, String sortDir, String type);

    APIResponse<String> deleteDrug(DrugDeleteRequest drugDeleteRequest);

    APIResponse<DrugsResponse>updateDrug(DrugsUpdateRequest drugsUpdateRequest);

}
