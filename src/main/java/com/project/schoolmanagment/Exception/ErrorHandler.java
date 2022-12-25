package com.project.schoolmanagment.Exception;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ErrorHandler implements ErrorController {
    private final ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public ApiErrorResponse handleException(WebRequest webRequest){
        Map<String,Object> attributes=this.errorAttributes.getErrorAttributes
                (webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.BINDING_ERRORS, ErrorAttributeOptions.Include.MESSAGE));
        String message=(String) attributes.get("message");
        Integer status=(Integer) attributes.get("status");
        String path=(String) attributes.get("path");
        ApiErrorResponse apiError=new ApiErrorResponse(message,status,path);
        if(attributes.containsKey("errors")){
            List<FieldError> fieldErrorList= (List<FieldError>) attributes.get("errors");
            Map<String,String> validationErrors=new HashMap<>();
            for(FieldError fieldError:fieldErrorList){
                validationErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            apiError.setValidations(validationErrors);
        }

        return apiError;
    }

    public String getErrorPath(){
        return "/error";
    }

}
