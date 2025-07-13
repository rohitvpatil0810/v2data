package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class NoEntryException extends ApiException {
    public NoEntryException(String message) {
        super(ErrorType.NO_ENTRY, message, null);
    }

    public NoEntryException() {
        super(ErrorType.NO_ENTRY, "No Entry Error", null);
    }
}

