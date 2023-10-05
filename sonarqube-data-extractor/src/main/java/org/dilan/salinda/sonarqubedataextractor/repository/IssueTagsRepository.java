package org.dilan.salinda.sonarqubedataextractor.repository;

import org.dilan.salinda.sonarqubedataextractor.model.Issue;
import org.dilan.salinda.sonarqubedataextractor.model.IssueTag;
import org.dilan.salinda.sonarqubedataextractor.model.IssueTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueTagsRepository extends JpaRepository<IssueTags, Long> {

    Optional<IssueTags> findIssueTagsByIssueTagAndIssue(IssueTag tag, Issue issue);

}
