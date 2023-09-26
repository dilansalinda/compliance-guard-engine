package org.dilan.salinda.sonarqubedataextractor.repository;

import org.dilan.salinda.sonarqubedataextractor.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
