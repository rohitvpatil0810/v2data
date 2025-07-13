package com.rohitvpatil0810.v2data.common.api.responses;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class TokenExpiredResponse<T> extends ApiResponse<T>{
    public TokenExpiredResponse(String message) {
        super(HttpStatus.UNAUTHORIZED, message, null, null, new HttpHeaders() {{
            set("instruction", "refresh_token");
        }});
    }

    public TokenExpiredResponse() {
        super(HttpStatus.UNAUTHORIZED, "Token Expired", null, null, new HttpHeaders() {{
            set("instruction", "refresh_token");
        }});
    }

}
