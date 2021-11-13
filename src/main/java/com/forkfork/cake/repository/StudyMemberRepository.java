package com.forkfork.cake.repository;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    List<StudyMember> findAllByStudy(Study studyById);
}
