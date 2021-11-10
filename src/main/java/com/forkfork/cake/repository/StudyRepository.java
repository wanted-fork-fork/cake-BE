package com.forkfork.cake.repository;

import com.forkfork.cake.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
