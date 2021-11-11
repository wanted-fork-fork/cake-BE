package com.forkfork.cake.controller.main;

import com.amazonaws.Response;
import com.forkfork.cake.domain.Category;
import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyCategory;
import com.forkfork.cake.domain.StudyFile;
import com.forkfork.cake.dto.paging.response.PagingResponse;
import com.forkfork.cake.service.S3Service;
import com.forkfork.cake.service.StudyCategoryService;
import com.forkfork.cake.service.StudyFileService;
import com.forkfork.cake.service.StudyService;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/page")
public class PagingController {
    private final StudyService studyService;
    private final StudyFileService studyFileService;
    private final S3Service s3Service;
    private final StudyCategoryService studyCategoryService;

    @GetMapping
    public ResponseEntity<Object> findAllStudy(@RequestParam int page) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        Slice<Study> studySlice = studyService.findStudyAll(pageRequest);

        List<PagingResponse> pagingResponseList = new LinkedList<>();
        for (Study study:
             studySlice) {
            List<StudyCategory> studyCategories = studyCategoryService.findStudyCategoryByStudy(study);
            List<String> give = new LinkedList<>();
            List<String> take = new LinkedList<>();
            String img = null;

            for (StudyCategory studyCategory:
                 studyCategories) {
                if (studyCategory.getType() == 1) {
                    //give
                    give.add(studyCategory.getCategory().getName());
                } else {
                    take.add(studyCategory.getCategory().getName());
                    img = studyCategory.getCategory().getImg();
                }
            }

            List<StudyFile> studyFileByStudy = studyFileService.findStudyFileByStudy(study);
            if (!studyFileByStudy.isEmpty()) {
                img = s3Service.getFileUrl(studyFileByStudy.get(0).getFile());
            }
            PagingResponse pagingResponse = new PagingResponse(study, img, give, take);
            pagingResponseList.add(pagingResponse);
        }

        return ResFormat.response(true, 200, pagingResponseList);
    }

    @PostMapping("/test")
    public String test() {
        for (int i = 0; i < 50; i++) {
            Study build = Study.builder().title(i + "번째 테스트 글").build();
            studyService.saveStudy(build);
        }

        return "good";
    }
}
