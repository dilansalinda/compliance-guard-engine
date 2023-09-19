package org.dilan.salinda.sonarqubedataextractor.DTO;

import lombok.*;

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
