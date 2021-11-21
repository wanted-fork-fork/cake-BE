package com.forkfork.cake.repository;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyMember;
import com.forkfork.cake.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    List<StudyMember> findAllByStudy(Study studyById);

    List<StudyMember> findAllByUser(User user);

    @Query("select s from StudyMember s where s.user=?1 and s.state=?2 order by s.id desc")
    List<StudyMember> findAllByUserAndState(User userByEmail, int state);

    StudyMember findByUserAndStudy(User userByEmail, Study studyById);
}
