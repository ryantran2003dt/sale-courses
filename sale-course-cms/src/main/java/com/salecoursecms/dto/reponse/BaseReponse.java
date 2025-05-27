package com.salecoursecms.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BaseReponse<T>{
    private int statusCode;
    private boolean isError;
    private String message;
    private PagingResponse pagingResponse;
    private T data;

    public BaseReponse(int statusCode, boolean isError, String message, T data) {
        this.statusCode = statusCode;
        this.isError = isError;
        this.message = message;
        this.data = data;
    }
    public BaseReponse(int statusCode, boolean isError, String message) {
        this.statusCode = statusCode;
        this.isError = isError;
        this.message = message;
    }
}
