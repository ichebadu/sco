package com.iche.sco.dto.globalResponse;

import com.iche.sco.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class APIResponse<T> {

    private String message;
    private String time;
    private int statusCode;
    private T data;
    public APIResponse( String message,int statusCode,T data){
        this.message = message;
        this.statusCode =statusCode;
        this.time = DateUtils.saveDate(LocalDateTime.now());
        this.data = data;
    }
}