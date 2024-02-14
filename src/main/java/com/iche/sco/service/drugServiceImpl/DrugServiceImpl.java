package com.iche.sco.service.drugServiceImpl;

import com.iche.sco.search.criteriaSearch.DrugCriteriaRepository;
import com.iche.sco.dto.drugs.request.DrugCreationRequest;
import com.iche.sco.dto.drugs.request.DrugDeleteRequest;
import com.iche.sco.search.specificationCriteriaSearch.SpecificationInput;
import com.iche.sco.dto.drugs.request.DrugsUpdateRequest;
import com.iche.sco.dto.drugs.response.DrugsResponse;
import com.iche.sco.search.specificationCriteriaSearch.DrugPageConstant;
import com.iche.sco.enums.DrugStatus;
import com.iche.sco.enums.MerchantType;
import com.iche.sco.enums.ResponseCode;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.exception.DrugNotFoundException;
import com.iche.sco.exception.InternalServerError;
import com.iche.sco.exception.InvalidCredentialsException;
import com.iche.sco.exception.UserNotFoundException;
import com.iche.sco.model.Drugs;
import com.iche.sco.model.BaseUser;
import com.iche.sco.model.Merchant;
import com.iche.sco.respository.DrugRepository;
import com.iche.sco.respository.MerchantRepository;
import com.iche.sco.search.specificationCriteriaSearch.DrugSpecifications;
import com.iche.sco.search.criteriaSearch.DrugCriteriaSearch;
import com.iche.sco.utils.DateUtils;
import com.iche.sco.utils.PageUtils;
import com.iche.sco.utils.UserVerification;
import com.iche.sco.utils.ValidationUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DrugServiceImpl implements DrugService {
    private final DrugRepository drugRepository;
    private final MerchantRepository merchantRepository;
    private final DrugCriteriaRepository drugCriteriaRepository;
    private final ValidationUtils<DrugCreationRequest> drugCreationRequestValidationUtils;
    private final UserVerification userVerification;


    @Override
    public APIResponse<DrugsResponse> createDrug(DrugCreationRequest drugCreationRequest) {
        drugCreationRequestValidationUtils.validate(drugCreationRequest);
        BaseUser baseUser = userVerification.validateLoginUser(drugCreationRequest.userEmail());
        Merchant merchant = (Merchant) baseUser;
        if(!(merchant.getMerchantType() == MerchantType.DRUG_VENDOR)){
            throw new InvalidCredentialsException("invalid merchant type");
        }

        if(drugRepository.existsByDrugName(drugCreationRequest.drugName())){
            throw new InternalServerError("drugs with this name already exist");
        }
        Drugs drugs = Drugs.builder()
                .packs(drugCreationRequest.packs())
                .drugName(drugCreationRequest.drugName())
                .price(drugCreationRequest.price())
                .drugStatus(DrugStatus.IN_STOCK)
                .merchant(validatedMerchant(drugCreationRequest.merchantId()))
                .createDate(LocalDateTime.now())
                .build();
        drugRepository.save(drugs);

        DrugsResponse response = DrugsResponse.builder()
                .id(drugs.getId())
                .name(drugs.getDrugName())
                .packs(drugs.getPacks())
                .price(drugs.getPrice())
                .drugStatus(DrugStatus.IN_STOCK)
                .merchantId(drugs.getMerchant().getId().toString())
                .createDate(DateUtils.saveDate(drugs.getCreateDate()))
                .build();

        return new APIResponse<>(ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }

    @Override
    public APIResponse<DrugsResponse> getSingleDrug(Long id) {

        Drugs drugs = drugRepository.findById(id).orElseThrow(
                ()-> new DrugNotFoundException("drug not found")
        );
        if(drugs.getDrugStatus() == DrugStatus.REMOVE ){
            throw new InvalidCredentialsException("drugs in not found or has been deleted");
        }
        DrugsResponse response = DrugsResponse.builder()
                .id(drugs.getId())
                .name(drugs.getDrugName())
                .packs(drugs.getPacks())
                .price(drugs.getPrice())
                .drugStatus(DrugStatus.IN_STOCK)
                .merchantId(String.valueOf(drugs.getMerchant().getId()))
                .createDate(DateUtils.saveDate(drugs.getCreateDate()))
                .updateDate(DateUtils.saveDate(drugs.getUpdateAt()))
                .build();
        return new APIResponse<>(ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.CREATE_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }

    @Override
    public APIResponse<PageUtils<DrugsResponse>> listOfDrugs(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);


        Page<Drugs> drugsPage = drugRepository.findAll(pageable);

        List<DrugsResponse> response = drugsPage.getContent().stream()
                .filter(drug -> drug.getDrugStatus() != DrugStatus.REMOVE)
                .map(drug -> DrugsResponse.builder()
                        .id(drug.getId())
                        .name(drug.getDrugName())
                        .price(drug.getPrice())
                        .packs(drug.getPacks())
                        .drugStatus(DrugStatus.IN_STOCK)
                        .merchantId(drug.getMerchant().getId().toString())
                        .createDate(DateUtils.saveDate(drug.getCreateDate()))
                        .updateDate(DateUtils.saveDate(drug.getUpdateAt()))
                        .build()
                ).collect(Collectors.toList());

        PageUtils<DrugsResponse> pageUtils = PageUtils.<DrugsResponse>builder()
                .content(response)
                .pageNo(drugsPage.getNumber())
                .pageSize(drugsPage.getSize())
                .totalElement(drugsPage.getTotalElements())
                .totalPage(drugsPage.getTotalPages())
                .isLast(drugsPage.isLast())
                .build();

        log.info("RESPONSE", response);
        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), pageUtils);
    }

    @Override
    public APIResponse<String> deleteDrug(DrugDeleteRequest drugDeleteRequest) {
        BaseUser baseUser = userVerification.validateLoginUser(drugDeleteRequest.userEmail());
        Merchant merchant = (Merchant) baseUser;
        if(!(merchant.getMerchantType() == MerchantType.DRUG_VENDOR)){
            throw new InvalidCredentialsException("invalid merchant type");
        }
        Drugs drugs = drugRepository.findById(drugDeleteRequest.id()).orElseThrow(
                ()->  new InternalServerError("Drug not found"));

        drugs.setDrugStatus(DrugStatus.REMOVE);
        drugRepository.save(drugs);
        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),"drugs deleted");
    }

    @Override
    public APIResponse<DrugsResponse> updateDrug(DrugsUpdateRequest drugsUpdateRequest) {
        BaseUser baseUser = userVerification.validateLoginUser(drugsUpdateRequest.userEmail());
        Merchant merchant =validatedMerchant(String.valueOf(drugsUpdateRequest.merchantId()));
        if(!(baseUser.getRoles().getRoleName().equals("ROLE_MERCHANT")) && merchant.getMerchantType() == MerchantType.DRUG_VENDOR){
            throw new InvalidCredentialsException("invalid credentials");
        }
        Drugs drug = drugRepository.findById(drugsUpdateRequest.id()).orElseThrow(
                ()-> new DrugNotFoundException("drugs not found"));
                drug.setDrugName(drugsUpdateRequest.drugName());
                drug.setPrice(drugsUpdateRequest.price());
                drug.setPacks(drugsUpdateRequest.packs());
                drug.setUpdateAt(LocalDateTime.now());

        drugRepository.save(drug);

        DrugsResponse response = DrugsResponse.builder()
                .id(drugsUpdateRequest.id())
                .name(drugsUpdateRequest.drugName())
                .price(drugsUpdateRequest.price())
                .packs(drugsUpdateRequest.packs())
                .merchantId(drugsUpdateRequest.merchantId().toString())
                .createDate(DateUtils.saveDate(drug.getCreateDate()))
                .updateDate(DateUtils.saveDate(drug.getUpdateAt()))
                .build();
        return new APIResponse<>(ResponseCode.UPDATE_DRUG_REQUEST_SUCCESSFUL.getMessage(),ResponseCode.UPDATE_DRUG_REQUEST_SUCCESSFUL.getStatusCode(),response);
    }

    @Override
    public APIResponse<List<DrugsResponse>> drugSearch(DrugPageConstant pageConstant, DrugCriteriaSearch drugCriteriaSearch) {
        Page<Drugs> allWithFilter = drugCriteriaRepository.findAllWithFilter(pageConstant, drugCriteriaSearch);

        List<DrugsResponse> response = allWithFilter.getContent().stream()
                .filter(drug -> drug.getDrugStatus() != DrugStatus.REMOVE)
                .map(drug -> DrugsResponse.builder()
                        .id(drug.getId())
                        .name(drug.getDrugName())
                        .price(drug.getPrice())
                        .packs(drug.getPacks())
                        .drugStatus(DrugStatus.IN_STOCK)
                        .merchantId(drug.getMerchant().getId().toString())
                        .createDate(DateUtils.saveDate(drug.getCreateDate()))
                        .updateDate(DateUtils.saveDate(drug.getUpdateAt()))
                        .build()
                ).collect(Collectors.toList());

        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), response);
    }
    @Override
    @Transactional(readOnly = true)
    public APIResponse<List<DrugsResponse>> drugSpecificationSearch(SpecificationInput specificationInput) {
        Specification<Drugs> specification = DrugSpecifications.byCriteria(specificationInput);

        // Use findAll(Specification<T> spec) instead of findAll(Pageable pageable)
        List<DrugsResponse> response = drugRepository.findAll(specification).stream()
                .filter(drug -> drug.getDrugStatus() != DrugStatus.REMOVE)
                .map(drug -> DrugsResponse.builder()
                        .id(drug.getId())
                        .name(drug.getDrugName())
                        .price(drug.getPrice())
                        .packs(drug.getPacks())
                        .drugStatus(DrugStatus.IN_STOCK)
                        .merchantId(drug.getMerchant().getId().toString())
                        .createDate(DateUtils.saveDate(drug.getCreateDate()))
                        .updateDate(DateUtils.saveDate(drug.getUpdateAt()))
                        .build()
                ).collect(Collectors.toList());
        log.info("EHSHJDJHWJDS" + response);

        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), response);
}
    private Merchant validatedMerchant(String merchantId){
        return merchantRepository.findById(Long.valueOf(merchantId))
                .orElseThrow( ()-> new UserNotFoundException("merchant with id: "+ merchantId + "not found"));
    }
}
