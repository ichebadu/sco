package com.iche.sco.service.drugServiceImpl;

import com.iche.sco.dto.request.DrugCreationRequest;
import com.iche.sco.dto.request.DrugsUpdateRequest;
import com.iche.sco.dto.response.ApiResponse;
import com.iche.sco.dto.response.DrugsResponse;

import java.util.List;

public interface DrugService {

    ApiResponse<DrugsResponse> createDrug(DrugCreationRequest drugCreationRequest);

    ApiResponse<DrugsResponse> getSingleDrug(Long id);

    ApiResponse<List<DrugsResponse>> listOfDrugs();

    ApiResponse<String> deleteDrug(Long id);

    ApiResponse<DrugsResponse>updateDrug(DrugsUpdateRequest drugsUpdateRequest);
}
