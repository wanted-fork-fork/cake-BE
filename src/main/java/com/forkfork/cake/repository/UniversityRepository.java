package com.forkfork.cake.repository;

import com.forkfork.cake.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
