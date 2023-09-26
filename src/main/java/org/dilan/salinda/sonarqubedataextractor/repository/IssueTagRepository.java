package org.dilan.salinda.sonarqubedataextractor.repository;

import org.dilan.salinda.sonarqubedataextractor.model.IssueTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository


public interface IssueTagRepository extends JpaRepository<IssueTag, Long> {
    Optional<IssueTag> findByName(String name);
}
