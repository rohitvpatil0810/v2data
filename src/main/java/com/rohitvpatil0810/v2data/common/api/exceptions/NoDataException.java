package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class NoDataException extends ApiException {
    public NoDataException(String message) {
        super(ErrorType.NO_DATA, message, null);
    }

    public NoDataException() {
        super(ErrorType.NO_DATA, "No Data Error", null);
    }
}
