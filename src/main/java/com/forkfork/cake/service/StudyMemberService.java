package com.forkfork.cake.service;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyMember;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.repository.StudyCategoryRepository;
import com.forkfork.cake.repository.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;

    public StudyMember saveStudyMember(StudyMember studyMember) {
        return studyMemberRepository.save(studyMember);
    }

    public List<StudyMember> findStudyMemberByStudy(Study studyById) {

        return studyMemberRepository.findAllByStudy(studyById);
    }

    public List<StudyMember> findStudyMemberByUserAndState(User userByEmail, int state) {
        return studyMemberRepository.findAllByUserAndState(userByEmail, state);
    }

    public List<StudyMember> findStudyMemberByUser(User userByEmail) {
        return studyMemberRepository.findAllByUser(userByEmail);
    }

    public StudyMember findStudyMemberById(Long studyMemberId) {
        return studyMemberRepository.findById(studyMemberId).orElse(null);
    }
}
