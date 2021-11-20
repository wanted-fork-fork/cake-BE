package com.forkfork.cake.repository;

import com.forkfork.cake.domain.ApplyFile;
import com.forkfork.cake.domain.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyFileRepository extends JpaRepository<ApplyFile, Long> {
    List<ApplyFile> findAllByStudyMember(StudyMember studyMemberById);
}
