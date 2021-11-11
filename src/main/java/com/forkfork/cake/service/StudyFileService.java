package com.forkfork.cake.service;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyFile;
import com.forkfork.cake.repository.StudyFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyFileService {
    private final StudyFileRepository studyFileRepository;

    public List<StudyFile> findStudyFileByStudy(Study study) {
        return studyFileRepository.findByStudy(study);
    }
}
