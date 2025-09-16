package com.rohitvpatil0810.v2data.common.api.exceptions;

import com.rohitvpatil0810.v2data.common.api.responses.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();

        result.getFieldErrors().forEach(error ->
                errorMap.put(error.getField(), error.getDefaultMessage())
        );

        return new ValidationErrorResponse(errorMap);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ApiResponse handleNotFoundException(NoResourceFoundException ex) {
        log.info("NOT FOUND - {} - {}", ex.getHttpMethod(), ex.getResourcePath());
        return new NotFoundResponse("Not Found : [" + ex.getHttpMethod() + "]" + ex.getResourcePath());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse handleGenericException(Exception ex) {
        if (ex instanceof ApiException) {
            return ApiException.handle((ApiException) ex);
        }
        log.error(String.valueOf(ex.fillInStackTrace()));
        return new InternalErrorResponse();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResponse handleMissingParamsException(MissingServletRequestParameterException ex) {
        return new BadRequestResponse<>(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse handleRunTimeException(RuntimeException ex) {
        if (ex.getCause() instanceof ApiException) {
            return ApiException.handle((ApiException) ex.getCause());
        }
        return new InternalErrorResponse();
    }
}
