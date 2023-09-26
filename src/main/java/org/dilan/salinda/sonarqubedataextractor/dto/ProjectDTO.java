package org.dilan.salinda.sonarqubedataextractor.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
