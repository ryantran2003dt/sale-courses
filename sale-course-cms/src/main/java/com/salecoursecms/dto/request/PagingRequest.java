package com.salecoursecms.dto.request;

import lombok.Data;

@Data
public class PagingRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private Boolean isASC;
}
