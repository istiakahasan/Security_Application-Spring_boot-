package com.SecurityApp.SecurityApplication.advice;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.awt.event.HierarchyBoundsAdapter;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiError {
    private LocalDateTime timeStamp;

    private String message;
    private HttpStatus status;
       private List<String> subErrors;
//    public ApiError(){
//        this.timeStamp=LocalDateTime.now();
//
//    }
    public ApiError(String message, HttpStatus status){

        this.message=message;
        this.status=status;
    }


}
