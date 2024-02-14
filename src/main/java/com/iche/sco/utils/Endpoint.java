package com.iche.sco.utils;

public interface Endpoint {
    String DRUG_BASE_URL = "/api/v1/drug";
    String CREATE= "/create";
    String DRUG_SEARCH = "/search";
    String DELETE = "/delete/{id}";
    String UPDATE = "/update";
    String SPECIFICATION_SEARCH = "/specification-search";

    String LIST_OF_DRUGS = "/list";
    String SINGLE_OF_DRUG = "/{id}";
    String AUTH_LOGOUT = "/logout";
    String REGISTRATION ="/registration";
    String MERCHANT_REGISTRATION ="/merchant-registration";
    String VERIFICATION = "/verification";
    String LOGIN = "login";
    String AUTH_BASE_URL = "api/v1/auth";

}
