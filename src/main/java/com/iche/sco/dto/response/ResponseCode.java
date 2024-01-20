package com.iche.sco.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ResponseCode {
    CREATE_DRUG_REQUEST_FAILED(476,"request processed"),
    UPDATE_DRUG_REQUEST_FAILED(785,"request processed"),
    VIEW_DRUG_REQUEST_FAILED(566,"request processed"),
    CREATE_DRUG_REQUEST_SUCCESSFUL(476,"request processed"),
    UPDATE_DRUG_REQUEST_SUCCESSFUL(785,"request processed"),
    VIEW_DRUG_REQUEST_SUCCESSFUL(566,"request processed"),
    DELETE_DRUG_REQUEST_SUCCESSFUL(796,"request processed"),
    DELETE_DRUG_REQUEST_FAILED(696,"request processed");

    private  int statusCode;
    private  String message;

}
