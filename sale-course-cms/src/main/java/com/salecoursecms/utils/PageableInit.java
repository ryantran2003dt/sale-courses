package com.salecoursecms.utils;

import com.salecoursecms.dto.request.PagingRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableInit {
    private PageableInit() {}
    public static Pageable getPageable(PagingRequest pagingRequestDTO) {
        int page = pagingRequestDTO.getPage() != null ? pagingRequestDTO.getPage() : 0;
        int size = pagingRequestDTO.getSize() == null || pagingRequestDTO.getSize() <= 0 ? 10 : pagingRequestDTO.getSize();
        String sortBy = pagingRequestDTO.getSortBy();
        boolean isASC = pagingRequestDTO.getIsASC() != null ? pagingRequestDTO.getIsASC() : Boolean.TRUE;

        if (StringUtils.isBlank(sortBy)) {
            return PageRequest.of(page, size);
        } else {
            if(Boolean.TRUE.equals(isASC)) {
                return PageRequest.of(page, size, Sort.by(sortBy));
            } else {
                return PageRequest.of(page, size, Sort.by(sortBy).descending());
            }
        }
    }
}
