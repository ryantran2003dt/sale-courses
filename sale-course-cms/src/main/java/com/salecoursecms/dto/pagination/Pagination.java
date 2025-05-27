package com.salecoursecms.dto.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pagination {
    private int totalPages; // tổng số trang
    private int currentPage; // trang hiện tại
    private int recordsPerPage = 10; // số tối đa bản ghi 1 trang
    private Integer page;
    private Integer size;
    public Pagination(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
        this.size = recordsPerPage;
    }
    public Pagination(int totalPages, int currentPage, int recordsPerPage, Integer page, Integer size) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.recordsPerPage = recordsPerPage;
        this.page = page;
        this.size = recordsPerPage;
    }
    public Pagination(int totalPages, int currentPage, int recordsPerPage) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.recordsPerPage = recordsPerPage;
        this.page = currentPage;
        this.size = recordsPerPage;
    }
}
