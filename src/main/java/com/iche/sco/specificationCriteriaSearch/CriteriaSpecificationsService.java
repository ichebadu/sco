package com.iche.sco.specificationCriteriaSearch;

import com.iche.sco.dto.drugs.response.DrugsResponse;
import com.iche.sco.dto.globalResponse.APIResponse;
import com.iche.sco.enums.DrugStatus;
import com.iche.sco.enums.ResponseCode;
import com.iche.sco.exception.DrugNotFoundException;
import com.iche.sco.exception.InputValidationException;
import com.iche.sco.model.Drugs;
import com.iche.sco.respository.DrugRepository;
import com.iche.sco.utils.DateUtils;
import com.iche.sco.utils.PageUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CriteriaSpecificationsService {
    private final DrugRepository drugRepository;


    public APIResponse<PageUtils<DrugsResponse>> getDrugsBetweenDates(SinglesSearchSpecification singlesSearchSpecification) throws ParseException {
        Specification<Drugs>  specification = getDrugSpecificationBetweenDate(singlesSearchSpecification);

        String sortColumn = singlesSearchSpecification.getSortColumn();
        String sortOrder = singlesSearchSpecification.getSortOrder();

        Integer pageNumber = 0;
        Integer pageSize = 10;

        boolean sortFlag = sortOrder.equalsIgnoreCase("ASC")?true: false;
        Sort sort = Sort.by(sortFlag? Sort.Direction.ASC: Sort.Direction.DESC,sortColumn);

        pageNumber = pageNumber>=0? singlesSearchSpecification.getPageNumber():pageNumber;
        pageSize = pageSize>=0? singlesSearchSpecification.getPageSize(): pageSize;

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Drugs> drugsPage = drugRepository.findAll(specification,pageable);

        List<DrugsResponse> responses = drugsPage.getContent().stream()
                .filter(drugs -> drugs.getDrugStatus() != DrugStatus.REMOVE)
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

        PageUtils<DrugsResponse> pageUtils =PageUtils.<DrugsResponse>builder()
                .content(responses)
                .pageNo(drugsPage.getNumber())
                .pageSize(drugsPage.getSize())
                .totalElement(drugsPage.getTotalElements())
                .totalPage(drugsPage.getTotalPages())
                .isLast(drugsPage.isLast())
                .build();

        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), pageUtils);

    }

    public APIResponse<List<DrugsResponse>> getDrugsByName(){
        Specification<Drugs> specification = getSpecification();
        log.info("DDDDFGGG{}", specification);

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
        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), response);

    }
    public APIResponse<List<DrugsResponse>> getDrugsData(SinglesSearchSpecification singlesSearchSpecification){
        Specification<Drugs> specification = getSpecification(singlesSearchSpecification);
        log.info("DDDDFGGG{}", specification);

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

        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), response);

    }
    public APIResponse<PageUtils<DrugsResponse>> getDrugByLike(SinglesSearchSpecification singlesSearchSpecification){
            Specification<Drugs> specification = getDrugSpecificationByLike(singlesSearchSpecification);

            String sortColumn = singlesSearchSpecification.getSortColumn();
            String sortOrder = singlesSearchSpecification.getSortOrder();

            Integer pageNumber = 0;
            Integer pageSize = 10;

            boolean sortFlag = sortOrder.equalsIgnoreCase("ASC")?true: false;
        Sort sort = Sort.by(sortFlag? Sort.Direction.ASC: Sort.Direction.DESC,sortColumn);

        pageNumber = pageNumber>=0? singlesSearchSpecification.getPageNumber():pageNumber;
        pageSize = pageSize>=0? singlesSearchSpecification.getPageSize(): pageSize;

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Drugs> drugsPage = drugRepository.findAll(specification,pageable);

        List<DrugsResponse> responses = drugsPage.getContent().stream()
                .filter(drugs -> drugs.getDrugStatus() != DrugStatus.REMOVE)
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

        PageUtils<DrugsResponse> pageUtils =PageUtils.<DrugsResponse>builder()
                .content(responses)
                .pageNo(drugsPage.getNumber())
                .pageSize(drugsPage.getSize())
                .totalElement(drugsPage.getTotalElements())
                .totalPage(drugsPage.getTotalPages())
                .isLast(drugsPage.isLast())
                .build();
      return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), pageUtils);

    }

    public APIResponse<PageUtils<DrugsResponse>> getGreaterThan(SinglesSearchSpecification singlesSearchSpecification){
        Specification<Drugs> specification = getSpecificationGreaterThan(singlesSearchSpecification);

        String sortColumn = singlesSearchSpecification.getSortColumn();
        String sortOrder = singlesSearchSpecification.getSortOrder();

        Integer pageNumber = 0;
        Integer pageSize = 10;

        boolean sortFlag = sortOrder.equalsIgnoreCase("ASC")?true: false;
        Sort sort = Sort.by(sortFlag? Sort.Direction.ASC: Sort.Direction.DESC,sortColumn);

        pageNumber = pageNumber>=0? singlesSearchSpecification.getPageNumber():pageNumber;
        pageSize = pageSize>=0? singlesSearchSpecification.getPageSize(): pageSize;

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Drugs> drugsPage = drugRepository.findAll(specification,pageable);

        List<DrugsResponse> responses = drugsPage.getContent().stream()
                .filter(drugs -> drugs.getDrugStatus() != DrugStatus.REMOVE)
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

        PageUtils<DrugsResponse> pageUtils =PageUtils.<DrugsResponse>builder()
                .content(responses)
                .pageNo(drugsPage.getNumber())
                .pageSize(drugsPage.getSize())
                .totalElement(drugsPage.getTotalElements())
                .totalPage(drugsPage.getTotalPages())
                .isLast(drugsPage.isLast())
                .build();
        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), pageUtils);


    }
    public APIResponse<PageUtils<DrugsResponse>> getLessThan(SinglesSearchSpecification singlesSearchSpecification){
        Specification<Drugs> specification = getSpecificationLessThan(singlesSearchSpecification);

        String sortColumn = singlesSearchSpecification.getSortColumn();
        String sortOrder = singlesSearchSpecification.getSortOrder();

        Integer pageNumber = 0;
        Integer pageSize = 10;

        boolean sortFlag = sortOrder.equalsIgnoreCase("ASC")?true: false;
        Sort sort = Sort.by(sortFlag? Sort.Direction.ASC: Sort.Direction.DESC,sortColumn);

        pageNumber = pageNumber>=0? singlesSearchSpecification.getPageNumber():pageNumber;
        pageSize = pageSize>=0? singlesSearchSpecification.getPageSize(): pageSize;

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Drugs> drugsPage = drugRepository.findAll(specification,pageable);

        List<DrugsResponse> responses = drugsPage.getContent().stream()
                .filter(drugs -> drugs.getDrugStatus() != DrugStatus.REMOVE)
                .map(drug -> DrugsResponse.builder()
                        .id(drug.getId())
                        .name(drug.getDrugName())
                        .price(drug.getPrice())
                        .packs(drug.getPacks())
                        .drugStatus(drug.getDrugStatus())
                        .merchantId(drug.getMerchant().getId().toString())
                        .createDate(DateUtils.saveDate(drug.getCreateDate()))
                        .updateDate(DateUtils.saveDate(drug.getUpdateAt()))
                        .build()
                ).collect(Collectors.toList());

        PageUtils<DrugsResponse> pageUtils =PageUtils.<DrugsResponse>builder()
                .content(responses)
                .pageNo(drugsPage.getNumber())
                .pageSize(drugsPage.getSize())
                .totalElement(drugsPage.getTotalElements())
                .totalPage(drugsPage.getTotalPages())
                .isLast(drugsPage.isLast())
                .build();
        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), pageUtils);

    }
    public APIResponse<String> getCountOfDeletedDrugs(SinglesSearchSpecification singlesSearchSpecification) {
        Specification<Drugs> specification = getSpecificationCountOfDeletedDrugs(singlesSearchSpecification);
        log.info("{}",specification);
        Long drugCount = drugRepository.count(specification);
        log.info("count {}",drugCount);
        boolean drugExist = drugRepository.exists(specification);
        log.info(String.valueOf(drugExist));
        BigDecimal bigDecimal = new BigDecimal(singlesSearchSpecification.getValue());

        List<Drugs> drug = drugRepository.findByPrice(bigDecimal)
                .stream()
                .filter(drugs -> drugs.getDrugStatus() != DrugStatus.REMOVE)
                .collect(Collectors.toList());
        log.info("DRUG {}",drug);

        if(!drugExist){
            throw new DrugNotFoundException("Drug not found");
        }
        for(Drugs drugs: drug){
            drugs.setDrugStatus(DrugStatus.REMOVE);
        }
        drugRepository.saveAll(drug);
        log.info(drugCount.toString());
        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), drugCount.toString());

    }

    public Specification<Drugs> getSpecificationList(List<SearchSpecification> searchSpecifications,String overAllOperation) {

        List<Predicate> predicateList = new ArrayList<>();
        return (root, query, criteriaBuilder) -> {


            for(SearchSpecification sf: searchSpecifications){

                String operation = sf.getOperation();

                switch (operation){
                    case "EQUAL" -> {
                        Predicate equal = criteriaBuilder.equal(root.get(sf.getColumnName()), sf.getValue());
                        predicateList.add(equal);
//                        if (specificationInput.getPrice() != null) {
//                            // Convert the String representation of price to BigDecimal
//                            BigDecimal price = new BigDecimal(String.valueOf(specificationInput.getPrice()));
//                            predicates.add(criteriaBuilder.equal(root.get("price"), price));
//                        }
                    }
                    case "GREATER_THAN" ->{
                        Predicate greaterThan = criteriaBuilder.greaterThan(root.get(sf.getColumnName()), sf.getValue());
                        predicateList.add(greaterThan);
                    }
                    case "GREATER_THAN_EQUAL" ->{
                        Predicate greaterThanOrEqualTo = criteriaBuilder.greaterThanOrEqualTo(root.get(sf.getColumnName()), sf.getValue());
                        predicateList.add(greaterThanOrEqualTo);
                    }
                    case "LESS_THAN" ->{
                        Predicate lessThan = criteriaBuilder.lessThan(root.get(sf.getColumnName()), sf.getValue());
                        predicateList.add(lessThan);
                    }
                    case "LESS_THAN_EQUAL" ->{
                        Predicate lessThanOrEqualTo = criteriaBuilder.lessThanOrEqualTo(root.get(sf.getColumnName()), sf.getValue());
                        predicateList.add(lessThanOrEqualTo);
                    }
                    case "LIKE" ->{
                        Predicate like = criteriaBuilder.like(root.get(sf.getColumnName()), "%" + sf.getValue() + "%");
                        predicateList.add(like);
                    }
                    case "IN" -> {
                        String[] sp = sf.getValue().split(",");
                        Predicate in = root.get(sf.getColumnName()).in(List.of(sp));
                        predicateList.add(in);
                    }
                    case "JOIN" ->{
                        Predicate join = criteriaBuilder.equal(root.join(sf.getColumnName()).get(sf.getJoinTable()),sf.getValue());
                        predicateList.add(join);
                    }
                }
            }
            if("AND".equalsIgnoreCase(overAllOperation)){
                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
            }
            else {
                return criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
            }
        };
    }

    public APIResponse<PageUtils<DrugsResponse>> drugSpecificationSearch(CriteriaSearchOperation criteriaSearchOperation) {
        if(criteriaSearchOperation.getValue() == null || criteriaSearchOperation.getValue().isEmpty()){
            throw new InputValidationException("input can not be empty");
        }
        Specification<Drugs> specification = searchList(criteriaSearchOperation);

        String sortColumn = criteriaSearchOperation.getSortColumn();
        String sortOrder = criteriaSearchOperation.getSortOrder();

        Integer pageNumber = 0;
        Integer pageSize = 10;

        boolean sortFlag = sortOrder.equalsIgnoreCase("ASC")?true: false;
        Sort sort = Sort.by(sortFlag? Sort.Direction.ASC: Sort.Direction.DESC,sortColumn);

        pageNumber = pageNumber>=0? criteriaSearchOperation.getPageNumber():pageNumber;
        pageSize = pageSize>=0? criteriaSearchOperation.getPageSize(): pageSize;

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Drugs> drugsPage = drugRepository.findAll(specification,pageable);

        List<Drugs> filterDrugList = drugsPage.getContent().stream()
                .filter(drugs -> drugs.getDrugStatus() != DrugStatus.REMOVE)
                .collect(Collectors.toList());

        List<DrugsResponse> response = filterDrugList.stream()
                .filter(drug -> drug.getDrugStatus() != DrugStatus.REMOVE)
                .map(drug -> DrugsResponse.builder()
                        .id(drug.getId())
                        .name(drug.getDrugName())
                        .price(drug.getPrice())
                        .packs(drug.getPacks())
                        .drugStatus(DrugStatus.IN_STOCK)
                        .merchantId(drug.getMerchant().getId().toString())
                        .createDate(drug.getCreateDate().toString())
                        .updateDate(DateUtils.saveDate(drug.getUpdateAt()))
                        .build()
                ).collect(Collectors.toList());
        int totalElements = (int) drugsPage.getTotalElements();
        int totalPages = drugsPage.getTotalPages();

        int filteredPageNumber = pageable.getPageNumber();
        int actualPageSize = response.size();

        PageUtils<DrugsResponse> pageUtils =PageUtils.<DrugsResponse>builder()
                .content(response)
                .pageNo(filteredPageNumber)
                .pageSize(actualPageSize)
                .totalElement(totalElements)
                .totalPage(totalPages)
                .isLast(drugsPage.isLast())
                .build();

        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), pageUtils);
    }
    public APIResponse<PageUtils<DrugsResponse>> drugListSpecificationSearch(SearchRequest searchRequest) {
        Specification<Drugs> specification = getSpecificationList(searchRequest.getSearchSpecifications(),searchRequest.getOverAllOperation());


        String sortColumn = searchRequest.getSearchSpecifications().get(0).getSortColumn();
        String sortOrder = searchRequest.getSearchSpecifications().get(0).getSortOrder();

        Integer pageNumber = searchRequest.getSearchSpecifications().get(0).getPageNumber() >= 0 ? searchRequest.getSearchSpecifications().get(0).getPageNumber() : 0;
        Integer pageSize = searchRequest.getSearchSpecifications().get(0).getPageSize() >= 0 ? searchRequest.getSearchSpecifications().get(0).getPageSize() : 10;

        boolean sortFlag = sortOrder.equalsIgnoreCase("ASC");
        Sort sort = Sort.by(sortFlag ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Drugs> drugsPage = drugRepository.findAll(specification, pageable);

        List<Drugs> filteredDrugsList = drugsPage.getContent().stream()
                .filter(drug -> drug.getDrugStatus() != DrugStatus.REMOVE)
                .collect(Collectors.toList());

        List<DrugsResponse> responses = filteredDrugsList.stream()
                .filter(drug -> drug.getDrugStatus() != DrugStatus.REMOVE)
                .map(drug -> DrugsResponse.builder()
                        .id(drug.getId())
                        .name(drug.getDrugName())
                        .price(drug.getPrice())
                        .packs(drug.getPacks())
                        .drugStatus(drug.getDrugStatus())
                        .merchantId(drug.getMerchant().getId().toString())
                        .createDate(drug.getCreateDate().toString())
                        .updateDate(DateUtils.saveDate(drug.getUpdateAt()))
                        .build()
                ).collect(Collectors.toList());

        PageUtils<DrugsResponse> pageUtils =PageUtils.<DrugsResponse>builder()
                .content(responses)
                .pageNo(drugsPage.getNumber())
                .pageSize(drugsPage.getSize())
                .totalElement(drugsPage.getTotalElements())
                .totalPage(drugsPage.getTotalPages())
                .isLast(drugsPage.isLast())
                .build();
        return new APIResponse<>(ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getMessage(), ResponseCode.VIEW_DRUG_REQUEST_SUCCESSFUL.getStatusCode(), pageUtils);

    }

    private Specification<Drugs> searchList(CriteriaSearchOperation criteriaSearchOperation) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            switch (criteriaSearchOperation.getOperation()) {
                case "EQUAL" -> {
                    Predicate equal = criteriaBuilder.equal(root.get(criteriaSearchOperation.getColumnName()), criteriaSearchOperation.getValue());
                    predicateList.add(equal);
                }
                case "GREATER_THAN" ->{
                    Predicate greaterThan = criteriaBuilder.greaterThan(root.get(criteriaSearchOperation.getColumnName()), criteriaSearchOperation.getValue());
                    predicateList.add(greaterThan);
                }
                case "GREATER_THAN_EQUAL" ->{
                    Predicate greaterThanOrEqualTo = criteriaBuilder.greaterThanOrEqualTo(root.get(criteriaSearchOperation.getColumnName()), criteriaSearchOperation.getValue());
                    predicateList.add(greaterThanOrEqualTo);
                }
                case "LESS_THAN" ->{
                    Predicate lessThan = criteriaBuilder.lessThan(root.get(criteriaSearchOperation.getColumnName()), criteriaSearchOperation.getValue());
                    predicateList.add(lessThan);
                }
                case "LESS_THAN_EQUAL" ->{
                    Predicate lessThanOrEqualTo = criteriaBuilder.lessThanOrEqualTo(root.get(criteriaSearchOperation.getColumnName()), criteriaSearchOperation.getValue());
                    predicateList.add(lessThanOrEqualTo);
                }
                case "LIKE" ->{
                    Predicate like = criteriaBuilder.like(root.get(criteriaSearchOperation.getColumnName()), "%" + criteriaSearchOperation.getValue() + "%");
                    predicateList.add(like);
                }
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }

    private  Specification<Drugs> getDrugSpecificationByLike(SinglesSearchSpecification singlesSearchSpecification){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(singlesSearchSpecification.getColumnName()), "%"+ singlesSearchSpecification.getValue() + "%" ));
    }
    private Specification<Drugs> getDrugSpecificationBetweenDate(SinglesSearchSpecification singlesSearchSpecification) throws ParseException {
        String value = singlesSearchSpecification.getValue();
        String[] values = value.split(",");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = simpleDateFormat.parse(values[0]);
        Date endDate = simpleDateFormat.parse(values[1]);
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(singlesSearchSpecification.getColumnName()),startDate,endDate);
    }
    private Specification<Drugs> getSpecification(){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("price"), 400);
    }
    private Specification<Drugs> getSpecification(SinglesSearchSpecification singlesSearchSpecification){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(singlesSearchSpecification.getColumnName()), singlesSearchSpecification.getValue());
    }
    private Specification<Drugs> getSpecificationGreaterThan(SinglesSearchSpecification singlesSearchSpecification){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get(singlesSearchSpecification.getColumnName()), singlesSearchSpecification.getValue());
    }
    private Specification<Drugs> getSpecificationLessThan(SinglesSearchSpecification singlesSearchSpecification){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(singlesSearchSpecification.getColumnName()), singlesSearchSpecification.getValue());
    }
    private Specification<Drugs> getSpecificationCountOfDeletedDrugs(SinglesSearchSpecification singlesSearchSpecification){
        return (root, query,criteriaBuilder) ->
            criteriaBuilder.equal(root.get(singlesSearchSpecification.getColumnName()), singlesSearchSpecification.getValue());
    }
}
