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
    private final S3Service s3Service;

    public List<StudyFile> findStudyFileByStudy(Study study) {
        return studyFileRepository.findByStudy(study);
    }

    public String findThumbnailImg(Study study) {
        String img = null;
        List<StudyFile> studyFileByStudy = studyFileRepository.findByStudy(study);
        if (!studyFileByStudy.isEmpty()) {
            img = s3Service.getFileUrl(studyFileByStudy.get(0).getFile());
        }

//        img null 이면 기본이미지 추가

        return img;
    }

    public String findThumbnailWithTakeSize(int size) {
        if (size < 2) {
            return "https://fork-fork-cake.s3.ap-northeast-2.amazonaws.com/thumbnail/piece.jpg";
        } else {
            return "https://fork-fork-cake.s3.ap-northeast-2.amazonaws.com/thumbnail/whole.jpg";
        }
    }

}
