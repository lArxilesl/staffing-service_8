// Database access layer
// Allows us to perform CRUD operations through Spring Data JPA on InternHiringStatus entities without manually writing SQL
package com.accenture.ems.emstraining.repository;

import com.accenture.ems.emstraining.entity.InternHiringStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternHiringStatusRepository
        extends JpaRepository<InternHiringStatusEntity, Long> {
}
