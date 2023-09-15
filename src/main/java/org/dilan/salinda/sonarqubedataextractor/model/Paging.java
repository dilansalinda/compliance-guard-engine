package org.dilan.salinda.sonarqubedataextractor.model;

import lombok.Data;

@Data
public class Paging {

    Long pageIndex;
    Long pageSize;
    Long total;

}
