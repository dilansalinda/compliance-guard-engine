package org.dilan.salinda.sonarqubedataextractor.DTO;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ProjectDTO implements Serializable {

    PagingDTO paging;
    List<String> organizations;
    List<ComponentsDTO> components;
    List<String> facets;

}
