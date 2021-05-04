package br.edu.utfpr.servicebook.util.pagination;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@Slf4j
public class PaginationUtil {
    public static final int PAGE_SIZE = 3;
    public static final int PAGE_GROUP_SIZE = 5;

    /**
     * Retorna o número da página de início a ser apresentada na lista de páginas
     * @param currentPage
     * @return
     */
    public static int getStartPage(int currentPage, int totalPages){
        log.debug("A página corrente é {}", currentPage);

        currentPage += 1;
        int remaining = totalPages - currentPage;
        int start = 1;
        if(remaining < (int)(PAGE_GROUP_SIZE/2)){
            start = currentPage - (PAGE_GROUP_SIZE - remaining) + 1;
        }
        else{
            if(currentPage <= (int)(PAGE_GROUP_SIZE/2)){
                start = 1;
            }
            else {
                start = currentPage - (int) (PAGE_GROUP_SIZE / 2);
            }
        }

        return (start <= 0)? 1 : start;
    }

    public static int getEndPage(int currentPage, int totalPages){
        log.debug("O total de páginas é {}", totalPages);

        int start = getStartPage(currentPage, totalPages);

        if(start + (PAGE_GROUP_SIZE-1) < totalPages){
            return start + (PAGE_GROUP_SIZE-1);
        }
        else{
            return totalPages;
        }
    }

    public static PaginationDTO getPaginationDTO(Page page){

        return new PaginationDTO(page.getTotalPages(), page.getNumber(), getStartPage(page.getNumber(), page.getTotalPages()), getEndPage(page.getNumber(), page.getTotalPages()), null);
    }

    public static PaginationDTO getPaginationDTO(Page page, String route){

        return new PaginationDTO(page.getTotalPages(), page.getNumber(), getStartPage(page.getNumber(), page.getTotalPages()), getEndPage(page.getNumber(), page.getTotalPages()), route);
    }
}
