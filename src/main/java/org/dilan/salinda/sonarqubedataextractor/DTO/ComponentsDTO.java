package org.dilan.salinda.sonarqubedataextractor.DTO;

import lombok.*;

import java.io.Serializable;
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
