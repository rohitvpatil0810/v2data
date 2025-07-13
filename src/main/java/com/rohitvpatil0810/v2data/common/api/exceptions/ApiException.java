package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;
import com.rohitvpatil0810.v2data.common.api.responses.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ApiException extends Exception {
    ErrorType type;
    String message = "Error";
    Object error;

    public static ApiResponse handle(ApiException ex){
        return switch (ex.type) {
            case BAD_TOKEN, ACCESS_TOKEN, UNAUTHORIZED ->
                    new AuthFailureResponse(ex.message);
            case TOKEN_EXPIRED -> new TokenExpiredResponse(ex.message);
            case INTERNAL -> new InternalErrorResponse(ex.message);
            case NOT_FOUND, NO_ENTRY, NO_DATA -> new NotFoundResponse(ex.message);
            case BAD_REQUEST -> new BadRequestResponse(ex.message);
            case DATA_VALIDATION -> new ValidationErrorResponse(ex.message, ex.error);
            case FORBIDDEN -> new ForbiddenErrorResponse(ex.message);
            default -> new InternalErrorResponse();
        };
    }
}
