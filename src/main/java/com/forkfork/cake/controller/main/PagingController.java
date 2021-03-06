package com.forkfork.cake.controller.main;

import com.forkfork.cake.domain.*;
import com.forkfork.cake.dto.category.SeperateCategoryDto;
import com.forkfork.cake.dto.paging.response.FilteringResponse;
import com.forkfork.cake.dto.paging.response.PagingResponse;
import com.forkfork.cake.service.*;
import com.forkfork.cake.util.JwtTokenUtil;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

        PageRequest pageRequest = PageRequest.of(page, 20, Sort.by("id").descending());
        Page<Study> studySlice = studyService.findStudyAll(pageRequest);

        List<PagingResponse> pagingResponseList = new LinkedList<>();
        for (Study study:
             studySlice) {
//            1. 시간지남, 2. 취소됨 3. 조기마감
            if ( study.getState() == 4 || !study.getUser().getUniversity().getName().equals(userByEmail.getUniversity().getName())) {
                continue;
            }

            List<StudyCategory> studyCategoryByStudy = studyCategoryService.findStudyCategoryByStudy(study);
            SeperateCategoryDto seperateCategoryDto = studyCategoryService.seperateCategory(studyCategoryByStudy);

            String img = studyFileService.findThumbnailImg(study);
            if (img == null) {
                img = studyFileService.findThumbnailWithTakeSize(seperateCategoryDto.getTake().size());
            }
            PagingResponse pagingResponse = new PagingResponse(study, img, seperateCategoryDto.getGive(), seperateCategoryDto.getTake());

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

        PageRequest pageRequest = PageRequest.of(page, 20, Sort.by("id").descending());
        Page<StudyCategory> studyCategoryByCategory = studyCategoryService.findStudyByfiltering(give, take, pageRequest);
        
        List<PagingResponse> pagingResponseList = new LinkedList<>();
        for (StudyCategory curStudy:
             studyCategoryByCategory) {
            Study study = curStudy.getStudy();
            if ( study.getState() == 4 || study.getType() != type || !study.getUser().getUniversity().getName().equals(userByEmail.getUniversity().getName())) {
                continue;
            }

            List<StudyCategory> studyCategoryByStudy = studyCategoryService.findStudyCategoryByStudy(study);
            SeperateCategoryDto seperateCategoryDto = studyCategoryService.seperateCategory(studyCategoryByStudy);

            String img = studyFileService.findThumbnailImg(study);
            if (img == null) {
                img = studyFileService.findThumbnailWithTakeSize(seperateCategoryDto.getTake().size());
            }

            PagingResponse pagingResponse = new PagingResponse(study, img, seperateCategoryDto.getGive(), seperateCategoryDto.getTake());

            pagingResponseList.add(pagingResponse);
        }

        Category giveCategory = categoryService.findCategoryById(give);
        Category takeCategory = categoryService.findCategoryById(take);

        FilteringResponse filteringResponse = new FilteringResponse(giveCategory.getName(), takeCategory.getName(), type);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("study", pagingResponseList);
        res.put("totalPage", studyCategoryByCategory.getTotalPages());
        res.put("filter", filteringResponse);

        return ResFormat.response(true, 200, res);
    }

}
