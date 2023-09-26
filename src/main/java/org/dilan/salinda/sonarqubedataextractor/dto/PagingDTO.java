package org.dilan.salinda.sonarqubedataextractor.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class PagingDTO implements Serializable {

    Long pageIndex;
    Long pageSize;
    Long total;

}
