package com.forkfork.cake.service;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

    public Study saveStudy(Study study) {
        return studyRepository.save(study);
    }

    public Page<Study> findStudyAll(PageRequest pageRequest) {
        return studyRepository.findAll(pageRequest);
    }
}
