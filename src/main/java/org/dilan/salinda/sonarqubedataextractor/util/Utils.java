package org.dilan.salinda.sonarqubedataextractor.util;

import org.dilan.salinda.sonarqubedataextractor.DTO.PagingDTO;

public class Utils {

    public static Long findMaxPages(PagingDTO pagingDTO) {
        return (pagingDTO.getTotal() + pagingDTO.getPageSize() - 1) / pagingDTO.getPageSize();
    }
}
