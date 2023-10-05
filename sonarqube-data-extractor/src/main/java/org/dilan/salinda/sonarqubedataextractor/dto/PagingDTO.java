package org.dilan.salinda.sonarqubedataextractor.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class PagingDTO {

    Long pageIndex;
    Long pageSize;
    Long total;

}
