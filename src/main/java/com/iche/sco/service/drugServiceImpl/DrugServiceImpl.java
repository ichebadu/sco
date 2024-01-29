package com.iche.sco.service.drugServiceImpl;

import com.iche.sco.dto.drugs.request.DrugCreationRequest;
import com.iche.sco.dto.drugs.request.DrugDeleteRequest;
import com.iche.sco.dto.drugs.request.DrugsUpdateRequest;
import com.iche.sco.dto.drugs.response.DrugsResponse;
import com.iche.sco.enums.ResponseCode;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.enums.Role;
import com.iche.sco.exception.DrugNotFoundException;
import com.iche.sco.exception.InternalServerError;
import com.iche.sco.exception.InvalidCredentialsException;
import com.iche.sco.model.Drugs;
import com.iche.sco.model.Users;
import com.iche.sco.respository.DrugRepository;
import com.iche.sco.utils.UserVerification;
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
    private final UserVerification userVerification;


    @Override
    public APIResponse<DrugsResponse> createDrug(DrugCreationRequest drugCreationRequest) {
        drugCreationRequestValidationUtils.validate(drugCreationRequest);
        Users users = userVerification.validateLoginUser(drugCreationRequest.userEmail());

        if(!(users.getRole().equals(Role.VENDOR) || users.getRole().equals(Role.ADMIN))){
            throw new InvalidCredentialsException("drugs with this name already exist");
        }

        if(drugRepository.existsByName(drugCreationRequest.drugName())){
            throw new InternalServerError("drugs with this name already exist");
        }
        Drugs drugs = Drugs.builder()
                .packs(drugCreationRequest.packs())
                .name(drugCreationRequest.drugName())
                .price(drugCreationRequest.price())
                .user(users)
                .build();
        drugRepository.save(drugs);

        DrugsResponse response = DrugsResponse.builder()
                .id(drugs.getId())
                .name(drugs.getName())
                .packs(drugs.getPacks())
                .price(drugs.getPrice())
                .build();

        return new APIResponse<>(ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }

    @Override
    public APIResponse<DrugsResponse> getSingleDrug(Long id) {

        Drugs drugs = drugRepository.findById(id).orElseThrow(
                ()-> new DrugNotFoundException("drug not found")
        );
        DrugsResponse response = DrugsResponse.builder()
                .id(drugs.getId())
                .name(drugs.getName())
                .packs(drugs.getPacks())
                .price(drugs.getPrice())
                .build();
        return new APIResponse<>(ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }

    @Override
    public APIResponse<List<DrugsResponse>> listOfDrugs() {

        List<Drugs> drugs = drugRepository.findAll();
        List<DrugsResponse> response = drugs.stream()
                .map(drug -> DrugsResponse.builder()
                        .id(drug.getId())
                        .name(drug.getName())
                        .price(drug.getPrice())
                        .packs(drug.getPacks())
                        .build()
                ).collect(Collectors.toList());
        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }

    @Override
    public APIResponse<String> deleteDrug(DrugDeleteRequest drugDeleteRequest) {
        Users users = userVerification.validateLoginUser(drugDeleteRequest.userEmail());

        if(!(users.getRole().equals(Role.VENDOR) || users.getRole().equals(Role.ADMIN))){
            throw new InvalidCredentialsException("drugs with this name already exist");
        }
        Drugs drugs = drugRepository.findById(drugDeleteRequest.id()).orElseThrow(
                ()->  new InternalServerError("Drug not found"));
        drugRepository.delete(drugs);
        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),"drugs deleted");
    }

    @Override
    public APIResponse<DrugsResponse> updateDrug(DrugsUpdateRequest drugsUpdateRequest) {
        Users users = userVerification.validateLoginUser(drugsUpdateRequest.userEmail());

        if(!(users.getRole().equals(Role.VENDOR) || users.getRole().equals(Role.ADMIN))){
            throw new InvalidCredentialsException("drugs with this name already exist");
        }
        Drugs drug = drugRepository.findById(drugsUpdateRequest.id()).orElseThrow(
                ()-> new DrugNotFoundException("drugs not found"));

                drug.setName(drugsUpdateRequest.drugName());
                drug.setPrice(drugsUpdateRequest.price());
                drug.setPacks(drugsUpdateRequest.packs());

        drugRepository.save(drug);

        DrugsResponse response = DrugsResponse.builder()
                .id(drugsUpdateRequest.id())
                .name(drugsUpdateRequest.drugName())
                .price(drugsUpdateRequest.price())
                .packs(drugsUpdateRequest.packs())
                .build();
        return new APIResponse<>(ResponseCode.UPDATE_DRUG_REQUEST_SUCCESSFUL.getMessage(),ResponseCode.UPDATE_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }
}
