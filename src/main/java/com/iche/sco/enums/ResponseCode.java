package com.iche.sco.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ResponseCode {
    CREATE_DRUG_REQUEST_FAILED(476,"request processed successfully"),
    UPDATE_DRUG_REQUEST_FAILED(785,"request processed successfully"),
    VIEW_DRUG_REQUEST_FAILED(566,"request processed successfully"),
    CREATE_DRUG_REQUEST_SUCCESSFUL(476,"request processed successfully"),
    UPDATE_DRUG_REQUEST_SUCCESSFUL(785,"request processed successfully"),
    VIEW_DRUG_REQUEST_SUCCESSFUL(566,"request processed successfully"),
    DELETE_DRUG_REQUEST_SUCCESSFUL(796,"request processed successfully"),
    DELETE_DRUG_REQUEST_FAILED(696,"request processed successfully"),
    USER_ALREADY_EXIST_RESPONSE(-991, "User already exists"),
    INVALID_CREDENTIALS_RESPONSE(-991, "Invalid credentials"),
    STAFF_STATUS_CHANGED_SUCCESS(991, "Staff status removed from organisation"),
    USER_CONTACT_CUSTOMER_CARE(991, "User should contact customer care"),
    STAFF_DELETED_FAILURE_RESPONSE(991, "Staff has already been removed from this organisation"),
    STAFF_ALREADY_REMOVED(991, "Staff has already been removed from this organisation"),

    TRANSACTION_PIN_RESPONSE(991, "invalid pin credential"),
    TRANSACTION_PIN_FAILURE(-991, "invalid pin credential"),
    TRANSACTION_PIN_VERIFICATION_SUCCESS(991, "pin verification success"),
    TRANSACTION_INITIALIZE_SUCCESS(891, "pin verification success"),
    TRANSACTION_PIN_VERIFICATION_FAILURE(-991, "pin verification failure"),
    USER_DISABLE_RESPONSE(-991, "User is disabled"),
    TOKEN_EXPIRED_RESPONSE(-991, "Token has expired"),
    TOKEN_NOT_FOUND_RESPONSE(-991, "Token not found"),
    UNAUTHORIZED_RESPONSE(-991, "Unauthorized access"),
    MESSAGE_FAILURE_RESPONSE(-991, "Message processing failed"),
    INPUT_NOT_VALID_RESPONSE(-991, "Invalid input data"),
    USER_NOT_FOUND_RESPONSE(-991, "User not found"),
    REGISTRATION_SUCCESS_RESPONSE(991, "Registration successful"),
    RESEND_OTP_SUCCESS_RESPONSE(991, "Otp resent successful"),
    MAIL_SUCCESS_RESPONSE(991, "Mail sent Successfully"),
    VERIFICATION_SUCCESS_RESPONSE(991, "Verification successful"),
    TRANSFER_PIN_SUCCESS_RESPONSE(991, "Transaction pin created successfully"),
    FORGOT_SUCCESS_RESPONSE(991, "Password reset request processed successfully"),
    PASSWORD_SUCCESS_RESPONSE(991, "Password processed successfully"),
    UPDATE_SUCCESS_RESPONSE(991, "Update successful"),
    LOGIN_SUCCESS_RESPONSE(991, "Login successful"),
    LOGOUT_SUCCESS_RESPONSE(991, "Logout successful"),
    STAFF_CREATION_SUCCESS_RESPONSE(991, "staff created successfully");
    private  int statusCode;
    private  String message;

}
