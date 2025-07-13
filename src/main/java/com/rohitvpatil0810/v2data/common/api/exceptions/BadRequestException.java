package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super(ErrorType.BAD_REQUEST, message, null);
    }

    public BadRequestException() {
        super(ErrorType.BAD_REQUEST, "Bad Request Error", null);
    }
}

