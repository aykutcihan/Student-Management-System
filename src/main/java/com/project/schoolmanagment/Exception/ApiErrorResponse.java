package com.project.schoolmanagment.Exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private String message;

    private Integer statusCode;

    private String path;

    private long timeStamp= new Date().getTime();

    private Map<String,String> validations;

    public ApiErrorResponse(String message, Integer statusCode, String path) {
        this.message = message;
        this.statusCode = statusCode;
        this.path = path;
    }
}
