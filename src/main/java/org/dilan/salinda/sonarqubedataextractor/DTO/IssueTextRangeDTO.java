package org.dilan.salinda.sonarqubedataextractor.DTO;

import lombok.Data;

@Data
public class IssueTextRangeDTO {

    private int startLine;
    private int endLine;
    private int startOffset;
    private int endOffset;

}
