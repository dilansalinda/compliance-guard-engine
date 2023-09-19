package org.dilan.salinda.sonarqubedataextractor.repository;

import org.dilan.salinda.sonarqubedataextractor.model.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long> {
}
