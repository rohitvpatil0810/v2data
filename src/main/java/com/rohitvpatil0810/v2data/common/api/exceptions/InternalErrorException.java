package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class InternalErrorException extends ApiException {
    public InternalErrorException(String message) {
        super(ErrorType.INTERNAL, message, null);
    }

    public InternalErrorException() {
        super(ErrorType.INTERNAL, "Internal Error", null);
    }
}
