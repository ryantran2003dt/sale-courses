package com.salecoursecms.dto.reponse;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponse {
    private int totalPages;
    private int page;
    private int size;
}
