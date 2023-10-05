package org.dilan.salinda.sonarqubedataextractor.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ComponentsDTO {

    private String organization;
    private String key;
    private String name;
    private boolean isFavorite;
    private List<String> tags;
    private String visibility;
    private Boolean isNew;

}
