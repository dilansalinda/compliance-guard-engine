package org.dilan.salinda.sonarqubedataextractor.dto;

import lombok.Data;

@Data
public class IssueTextRangeDTO {

    private int startLine;
    private int endLine;
    private int startOffset;
    private int endOffset;

}
