package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super(ErrorType.FORBIDDEN, message, null);
    }

    public ForbiddenException() {
        super(ErrorType.FORBIDDEN, "Forbidden Error", null);
    }
}

