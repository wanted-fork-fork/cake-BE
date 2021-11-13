package com.forkfork.cake.repository;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyFileRepository extends JpaRepository<StudyFile, Long> {
    List<StudyFile> findByStudy(Study study);
}
