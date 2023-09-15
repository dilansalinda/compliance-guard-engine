package org.dilan.salinda.sonarqubedataextractor.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class Project {

    Paging paging;
    List<String> organizations;
    List<Components> components;
    List<String> facets;

}
