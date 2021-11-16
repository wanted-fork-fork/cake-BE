package com.forkfork.cake.service;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyCategory;
import com.forkfork.cake.domain.StudyFile;
import com.forkfork.cake.dto.paging.response.PagingResponse;
import com.forkfork.cake.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyCategoryService studyCategoryService;
    private final StudyFileService studyFileService;
    private final S3Service s3Service;

    public Study saveStudy(Study study) {
        return studyRepository.save(study);
    }

    public Page<Study> findStudyAll(PageRequest pageRequest) {
        return studyRepository.findAll(pageRequest);
    }

    public Study findStudyById(Long id) {
        return studyRepository.findById(id).orElse(null);
    }

}
