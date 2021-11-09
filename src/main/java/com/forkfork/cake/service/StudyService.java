package com.forkfork.cake.service;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

    public Study saveStudy(Study study) {
        return studyRepository.save(study);
    }
}
