package org.dilan.salinda.sonarqubedataextractor.DTO;

import lombok.Data;

import java.util.List;

@Data
public class IssueSearchDTO {

    private int total;
    private int p;
    private int ps;
    private PagingDTO paging;
    private int effortTotal;
    private int debtTotal;
    private List<IssuesDTO> issues;
    private List<ComponentDTO> components;
    private List<OrganizationDTO> organizations;
    private List<String> facets;



    @Data
    public static class ComponentDTO {
        private String organization;
        private String key;
        private String uuid;
        private boolean enabled;
        private String qualifier;
        private String name;
        private String longName;
        private String path;
    }

    @Data
    public static class OrganizationDTO {
        private String key;
        private String name;
    }
}
