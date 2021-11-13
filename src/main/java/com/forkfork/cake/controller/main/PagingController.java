package com.forkfork.cake.controller.main;

import com.amazonaws.Response;
import com.forkfork.cake.domain.Category;
import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyCategory;
import com.forkfork.cake.domain.StudyFile;
import com.forkfork.cake.dto.paging.response.PagingResponse;
import com.forkfork.cake.service.*;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Object> findAllStudy(@RequestParam int page) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        Slice<Study> studySlice = studyService.findStudyAll(pageRequest);

        List<PagingResponse> pagingResponseList = new LinkedList<>();
        for (Study study:
             studySlice) {
            if ((study.getStartDate() != null && study.getStartDate().before(new Date())) || study.getEarlyClosing()) {
                continue;
            }

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

    @GetMapping("/filter")
    public ResponseEntity<Object> findFilterStudy(@RequestParam int page, @RequestParam Long give, @RequestParam Long take, @RequestParam int type) {

        PageRequest pageRequest = PageRequest.of(page, 20);
        List<StudyCategory> studyCategoryByCategory = studyCategoryService.findStudyByfiltering(give, take, pageRequest);

        List<PagingResponse> pagingResponseList = new LinkedList<>();
        for (StudyCategory curStudy:
             studyCategoryByCategory) {
            Study study = curStudy.getStudy();

            if ((study.getStartDate() != null && study.getStartDate().before(new Date())) || study.getEarlyClosing() || study.getType() != type) {
                continue;
            }

            List<StudyCategory> studyCategories = studyCategoryService.findStudyCategoryByStudy(study);
            List<String> giveCategory = new LinkedList<>();
            List<String> takeCategory = new LinkedList<>();
            String img = null;

            for (StudyCategory studyCategory:
                    studyCategories) {
                if (studyCategory.getType() == 1) {
                    //give
                    giveCategory.add(studyCategory.getCategory().getName());
                } else {
                    takeCategory.add(studyCategory.getCategory().getName());
                    img = studyCategory.getCategory().getImg();
                }
            }

            List<StudyFile> studyFileByStudy = studyFileService.findStudyFileByStudy(study);
            if (!studyFileByStudy.isEmpty()) {
                img = s3Service.getFileUrl(studyFileByStudy.get(0).getFile());
            }
            PagingResponse pagingResponse = new PagingResponse(study, img, giveCategory, takeCategory);
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
