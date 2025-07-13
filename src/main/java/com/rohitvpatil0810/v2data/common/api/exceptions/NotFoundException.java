package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(ErrorType.NOT_FOUND, message, null);
    }

    public NotFoundException() {
        super(ErrorType.NOT_FOUND, "Not Found Error", null);
    }
}
