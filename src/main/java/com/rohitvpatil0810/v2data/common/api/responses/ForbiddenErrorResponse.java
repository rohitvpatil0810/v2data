package com.rohitvpatil0810.v2data.common.api.responses;

import org.springframework.http.HttpStatus;

public class ForbiddenErrorResponse<T> extends ApiResponse<T> {
    public ForbiddenErrorResponse(String message){
        super(HttpStatus.FORBIDDEN, message, null, null, null);
    }

    public ForbiddenErrorResponse(){
        super(HttpStatus.FORBIDDEN, "Forbidden", null, null, null);
    }
}
