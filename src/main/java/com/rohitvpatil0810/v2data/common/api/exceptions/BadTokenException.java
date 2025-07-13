package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class BadTokenException extends ApiException{
    public BadTokenException(String message){
        super(ErrorType.BAD_TOKEN, message, null);
    }

    public BadTokenException() {
        super(ErrorType.BAD_TOKEN, "Bad Token Error", null);
    }
}
