package org.dilan.salinda.sonarqubedataextractor.repository;


import org.dilan.salinda.sonarqubedataextractor.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByNameAndKey(String name, String key);
}
