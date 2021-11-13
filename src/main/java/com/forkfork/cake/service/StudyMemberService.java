package com.forkfork.cake.service;

import com.forkfork.cake.domain.StudyMember;
import com.forkfork.cake.repository.StudyCategoryRepository;
import com.forkfork.cake.repository.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyMemberService {

    private final StudyMemberRepository studyMemberRepository;

    public StudyMember saveStudyMember(StudyMember studyMember) {
        return studyMemberRepository.save(studyMember);
    }
}
