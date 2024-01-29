package com.iche.sco.service.drugServiceImpl;

import com.iche.sco.dto.drugs.request.DrugCreationRequest;
import com.iche.sco.dto.drugs.request.DrugDeleteRequest;
import com.iche.sco.dto.drugs.request.DrugsUpdateRequest;
import com.iche.sco.dto.drugs.response.DrugsResponse;
import com.iche.sco.dto.globalResponse.APIResponse;

import java.util.List;

public interface DrugService {

    APIResponse<DrugsResponse> createDrug(DrugCreationRequest drugCreationRequest);

    APIResponse<DrugsResponse> getSingleDrug(Long id);

    APIResponse<List<DrugsResponse>> listOfDrugs();

    APIResponse<String> deleteDrug(DrugDeleteRequest drugDeleteRequest);

    APIResponse<DrugsResponse>updateDrug(DrugsUpdateRequest drugsUpdateRequest);
}
