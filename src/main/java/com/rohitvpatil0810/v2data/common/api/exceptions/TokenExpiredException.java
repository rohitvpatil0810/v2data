package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.enums.ErrorType;

public class TokenExpiredException extends ApiException{
    public TokenExpiredException(String message){
        super(ErrorType.TOKEN_EXPIRED, message, null);
    }

    public TokenExpiredException(){
        super(ErrorType.TOKEN_EXPIRED, "Token Expired Error", null);
    }
}
