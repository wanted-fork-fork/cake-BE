package com.forkfork.cake.controller.main;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyCategory;
import com.forkfork.cake.domain.StudyFile;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.dto.paging.response.PagingResponse;
import com.forkfork.cake.service.*;
import com.forkfork.cake.util.JwtTokenUtil;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/page")
public class PagingController {
    private final StudyService studyService;
    private final StudyFileService studyFileService;
    private final S3Service s3Service;
    private final StudyCategoryService studyCategoryService;
    private final CategoryService categoryService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> findAllStudy(HttpServletRequest request, @RequestParam int page) {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);

        PageRequest pageRequest = PageRequest.of(page, 20);
        Page<Study> studySlice = studyService.findStudyAll(pageRequest);

        List<PagingResponse> pagingResponseList = new LinkedList<>();
        for (Study study:
             studySlice) {

            if ((study.getStartDate() != null && study.getStartDate().before(new Date())) || study.getCancellation() || study.getEarlyClosing() || (study.getUser() == null || !study.getUser().getUniversity().getName().equals(userByEmail.getUniversity().getName()))) {
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

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("study", pagingResponseList);
        res.put("totalPage", studySlice.getTotalPages());

        return ResFormat.response(true, 200, res);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> findFilterStudy(HttpServletRequest request, @RequestParam int page, @RequestParam Long give, @RequestParam Long take, @RequestParam int type) {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);

        PageRequest pageRequest = PageRequest.of(page, 20);
        Page<StudyCategory> studyCategoryByCategory = studyCategoryService.findStudyByfiltering(give, take, pageRequest);

        List<PagingResponse> pagingResponseList = new LinkedList<>();
        for (StudyCategory curStudy:
             studyCategoryByCategory) {
            Study study = curStudy.getStudy();

            if ((study.getStartDate() != null && study.getStartDate().before(new Date())) || study.getCancellation() || study.getEarlyClosing() || study.getType() != type || (study.getUser() == null || !study.getUser().getUniversity().getName().equals(userByEmail.getUniversity().getName()))) {
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

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("study", pagingResponseList);
        res.put("totalPage", studyCategoryByCategory.getTotalPages());

        return ResFormat.response(true, 200, res);
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
