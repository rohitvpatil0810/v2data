package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(ErrorType.UNAUTHORIZED, message, null);
    }

    public UnauthorizedException() {
        super(ErrorType.UNAUTHORIZED, "Unauthorized Error", null);
    }
}
