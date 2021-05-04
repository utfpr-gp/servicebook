package br.edu.utfpr.servicebook.util.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO {
    private int totalPages;
    private int currentPage;
    private int startPage;
    private int endPage;
    private String route;
}
