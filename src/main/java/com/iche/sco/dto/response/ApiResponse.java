package com.iche.sco.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiResponse <T>{
    private String message;
    private String time;
    private int statusCode;
    private T data;

    public ApiResponse(String message, int statusCode,T data){
        this.message = message;
        this.time = String.valueOf(System.currentTimeMillis());
        this.statusCode = statusCode;
        this.data = data;
    }
}
