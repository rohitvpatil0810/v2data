package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class DataValidationException extends ApiException {
    public DataValidationException(String message, Object error) {
        super(ErrorType.DATA_VALIDATION, message, error);
    }

    public DataValidationException(Object error) {
        super(ErrorType.DATA_VALIDATION, "Data Validation Error", error);
    }
}
