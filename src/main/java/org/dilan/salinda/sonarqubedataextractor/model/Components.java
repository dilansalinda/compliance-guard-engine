package org.dilan.salinda.sonarqubedataextractor.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.List;


@Data
public class Components {

    String organization;
    String key;
    String name;
    boolean isFavorite;
    List<String> tags;

    String visibility;

    boolean isNew;
}
