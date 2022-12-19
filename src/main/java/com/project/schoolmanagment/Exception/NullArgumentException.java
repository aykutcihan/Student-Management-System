package com.project.schoolmanagment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullArgumentException extends RuntimeException{

    public NullArgumentException(String message){
        super(message);

    }
}
