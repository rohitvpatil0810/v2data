package com.rohitvpatil0810.v2data.common.api.exceptions;


import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class AccessTokenException extends ApiException {
    public AccessTokenException(String message) {
        super(ErrorType.ACCESS_TOKEN, message, null);
    }

    public AccessTokenException() {
        super(ErrorType.ACCESS_TOKEN, "Access Token Error", null);
    }
}
