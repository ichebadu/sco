package com.iche.sco.exception;

import com.iche.sco.dto.response.ApiResponse;
import com.iche.sco.dto.response.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<ApiResponse<ExceptionResponse>> inputValidationException(InputValidationException e, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = getExceptionResponse(e, httpServletRequest);

        ApiResponse apiResponse = ApiResponse.builder()
                .message(ResponseCode.CREATE_DRUG_REQUEST_FAILED.getMessage())
                .time(String.valueOf(System.currentTimeMillis()))
                .statusCode(ResponseCode.CREATE_DRUG_REQUEST_FAILED.getStatusCode())
                .data(exceptionResponse)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ApiResponse<ExceptionResponse>> InternalServerError(InternalServerError e, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = getExceptionResponse(e, httpServletRequest);

        ApiResponse apiResponse = ApiResponse.builder()
                .message(ResponseCode.CREATE_DRUG_REQUEST_FAILED.getMessage())
                .time(String.valueOf(System.currentTimeMillis()))
                .statusCode(ResponseCode.CREATE_DRUG_REQUEST_FAILED.getStatusCode())
                .data(exceptionResponse)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DrugNotFoundException.class)
    public ResponseEntity<ApiResponse<ExceptionResponse>> DrugNotFoundException(DrugNotFoundException e, HttpServletRequest httpServletRequest){
        ExceptionResponse exceptionResponse = getExceptionResponse(e, httpServletRequest);

        ApiResponse apiResponse = ApiResponse.builder()
                .message(ResponseCode.CREATE_DRUG_REQUEST_FAILED.getMessage())
                .time(String.valueOf(System.currentTimeMillis()))
                .statusCode(ResponseCode.CREATE_DRUG_REQUEST_FAILED.getStatusCode())
                .data(exceptionResponse)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static  <T extends Exception> ExceptionResponse getExceptionResponse(T e, HttpServletRequest httpServletRequest) {
        ExceptionResponse exceptionResponse  = ExceptionResponse.builder()
                .message(e.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();
        return exceptionResponse;
    }

}
