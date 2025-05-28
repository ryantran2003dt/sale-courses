package com.salecourseweb.exception;

import com.salecourseweb.constant.AppConst;
import com.salecourseweb.constant.VariableConst;
import com.salecourseweb.dto.reponse.BaseReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler({SystemErrorException.class})
    @ResponseStatus(HttpStatus.OK)
    public BaseReponse<Object> handleSystemErrorException (SystemErrorException exception) {
        return BaseReponse.builder()
                .message(messageSource.getMessage(exception.getMessage(), null, new Locale(VariableConst.LAN)))
                .isError(Boolean.TRUE)
                .data(null)
                .statusCode(AppConst.STATUS_FAIL)
                .build();
    }

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.OK)
    public BaseReponse<Object> handleUnauthorizedException (UnauthorizedException exception) {
        return BaseReponse.builder()
                .message(messageSource.getMessage(exception.getMessage(), null, new Locale(VariableConst.LAN)))
                .isError(Boolean.TRUE)
                .data(null)
                .statusCode(AppConst.STATUS_FAIL)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseReponse<Object> handleValidationException(MethodArgumentNotValidException exception) {
        String messageKey = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return BaseReponse.builder()
                .message(messageSource.getMessage(messageKey, null, new Locale(VariableConst.LAN)))
                .isError(true)
                .statusCode(AppConst.STATUS_FAIL)
                .data(null)
                .build();
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseReponse<Object> handleJwtException(JwtException exception) {
        return BaseReponse.builder()
                .message(messageSource.getMessage(exception.getMessage(), null, new Locale(VariableConst.LAN)))
                .isError(true)
                .statusCode(AppConst.STATUS_FAIL)
                .data(null)
                .build();
    }


    @ExceptionHandler({com.salecourseweb.exception.Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public BaseReponse<Object> handleGeneralException (Exception exception) {
        return BaseReponse.builder()
                .message(messageSource.getMessage(exception.getMessage(), null, new Locale(VariableConst.LAN)))
                .isError(Boolean.TRUE)
                .data(null)
                .statusCode(AppConst.STATUS_FAIL)
                .build();
    }
}
