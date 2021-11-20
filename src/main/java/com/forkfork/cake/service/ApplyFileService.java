package com.forkfork.cake.service;

import com.forkfork.cake.domain.ApplyFile;
import com.forkfork.cake.domain.StudyMember;
import com.forkfork.cake.repository.ApplyFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyFileService {

    private final ApplyFileRepository applyFileRepository;

    public List<ApplyFile> findAllApplyFileByStudyMember(StudyMember studyMemberById) {
        return applyFileRepository.findAllByStudyMember(studyMemberById);
    }
}
