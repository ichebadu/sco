package com.iche.sco.service.drugServiceImpl;

import com.iche.sco.dto.request.DrugCreationRequest;
import com.iche.sco.dto.request.DrugsUpdateRequest;
import com.iche.sco.dto.response.ApiResponse;
import com.iche.sco.dto.response.DrugsResponse;
import com.iche.sco.dto.response.ResponseCode;
import com.iche.sco.exception.DrugNotFoundException;
import com.iche.sco.exception.InternalServerError;
import com.iche.sco.model.Drugs;
import com.iche.sco.respository.DrugRepository;
import com.iche.sco.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DrugServiceImpl implements DrugService {
    private final DrugRepository drugRepository;
    private final ValidationUtils<DrugCreationRequest> drugCreationRequestValidationUtils;


    @Override
    public ApiResponse<DrugsResponse> createDrug(DrugCreationRequest drugCreationRequest) {
        drugCreationRequestValidationUtils.validate(drugCreationRequest);

        if(drugRepository.existsByName(drugCreationRequest.name())){
            throw new InternalServerError("drugs with this name already exist");
        }

        Drugs drugs = Drugs.builder()
                .packs(drugCreationRequest.packs())
                .name(drugCreationRequest.name())
                .price(drugCreationRequest.price())
                .build();
        drugRepository.save(drugs);

        DrugsResponse response = DrugsResponse.builder()
                .id(drugs.getId())
                .name(drugs.getName())
                .packs(drugs.getPacks())
                .price(drugs.getPrice())
                .build();
        return new ApiResponse<>(ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }


    @Override
    public ApiResponse<DrugsResponse> getSingleDrug(Long id) {

        Drugs drugs = drugRepository.findById(id).orElseThrow(
                ()-> new DrugNotFoundException("drug not found")
        );
        DrugsResponse response = DrugsResponse.builder()
                .id(drugs.getId())
                .name(drugs.getName())
                .packs(drugs.getPacks())
                .price(drugs.getPrice())
                .build();
        return new ApiResponse<>(ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }

    @Override
    public ApiResponse<List<DrugsResponse>> listOfDrugs() {

        List<Drugs> drugs = drugRepository.findAll();
        List<DrugsResponse> response = drugs.stream()
                .map(drug -> DrugsResponse.builder()
                        .id(drug.getId())
                        .name(drug.getName())
                        .price(drug.getPrice())
                        .packs(drug.getPacks())
                        .build()
                ).collect(Collectors.toList());
        return new ApiResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }

    @Override
    public ApiResponse<String> deleteDrug(Long id) {
        Drugs drugs = drugRepository.findById(id).orElseThrow(
                ()->  new InternalServerError("Drug not found"));
        drugRepository.delete(drugs);
        return new ApiResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),"drugs deleted");
    }

    @Override
    public ApiResponse<DrugsResponse> updateDrug(DrugsUpdateRequest drugsUpdateRequest) {

        if(!drugRepository.existsById(drugsUpdateRequest.id())){
            throw new DrugNotFoundException("drug not found");
        }
        Drugs drug = Drugs.builder()
                .name(drugsUpdateRequest.name())
                .price(drugsUpdateRequest.price())
                .packs(drugsUpdateRequest.packs())
                .build();
        drugRepository.save(drug);

        DrugsResponse response = DrugsResponse.builder()
                .id(drugsUpdateRequest.id())
                .name(drugsUpdateRequest.name())
                .price(drugsUpdateRequest.price())
                .packs(drugsUpdateRequest.packs())
                .build();
        return new ApiResponse<>(ResponseCode.UPDATE_DRUG_REQUEST_SUCCESSFUL.getMessage(),ResponseCode.UPDATE_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }
}
