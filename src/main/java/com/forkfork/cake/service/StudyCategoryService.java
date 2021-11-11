package com.forkfork.cake.service;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyCategory;
import com.forkfork.cake.repository.StudyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyCategoryService {
    private final StudyCategoryRepository studyCategoryRepository;

    public List<StudyCategory> findStudyCategoryByStudy(Study study) {
        return studyCategoryRepository.findByStudy(study);
    }
}
