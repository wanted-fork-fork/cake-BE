package com.forkfork.cake.controller.study;

import com.forkfork.cake.domain.*;
import com.forkfork.cake.dto.study.request.SaveStudyRequest;
import com.forkfork.cake.dto.study.response.FindStudyDetailResponse;
import com.forkfork.cake.dto.study.response.UserInformationDto;
import com.forkfork.cake.service.*;
import com.forkfork.cake.util.AES128Encoder;
import com.forkfork.cake.util.JwtTokenUtil;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private final StudyMemberService studyMemberService;
    private final S3Service s3Service;
    private final ReviewService reviewService;
    private final StudyFileService studyFileService;
    private final StudyCategoryService studyCategoryService;

    @Value("${KEY.AES128}")
    private String AES128KEY;

    @PostMapping
    public ResponseEntity<Object> saveStudy(HttpServletRequest request, @RequestBody SaveStudyRequest saveStudyRequest) throws Exception {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);

        AES128Encoder aes128Encoder = new AES128Encoder(AES128KEY);
        String roomPwd = saveStudyRequest.getRoomPwd();
        String encrypt = aes128Encoder.encrypt(roomPwd);

        Study study = saveStudyRequest.toStudyEntity(userByEmail, encrypt);

        for (String img:
             saveStudyRequest.getImages()) {
            StudyFile build = StudyFile.builder().file(img).study(study).build();
            study.addStudyFile(build);
        }

        for (Long give:
             saveStudyRequest.getGive()) {
            Category categoryById = categoryService.findCategoryById(give);
            StudyCategory build = StudyCategory.builder().category(categoryById).study(study).type(1).build();
            study.addStudyCategory(build);
        }

        for (Long take:
                saveStudyRequest.getTake()) {
            Category categoryById = categoryService.findCategoryById(take);
            StudyCategory build = StudyCategory.builder().category(categoryById).study(study).type(2).build();
            study.addStudyCategory(build);
        }

        Study savedStudy = studyService.saveStudy(study);
        StudyMember studyMember = StudyMember.builder().study(savedStudy).user(userByEmail).state(1).build();

        studyMemberService.saveStudyMember(studyMember);

        return ResFormat.response(true, 201, "스터디 생성을 완료했습니다.");

    }

    @GetMapping
    public ResponseEntity<Object> findStudyDetail(@RequestParam Long id) {
        Study studyById = studyService.findStudyById(id);
        User user = studyById.getUser();

        String fileUrl = s3Service.getFileUrl(user.getImg());

        Double rate = null;
        List<Review> allReviewByToUser = reviewService.findAllReviewByToUser(user);

        Long cnt = 0L;
        Double point = 0D;
        for (Review review :
             allReviewByToUser) {
            cnt += 1;
            point += review.getReviewPoint();
        }

        if (cnt != 0) {
            rate = point / cnt;
        }

        UserInformationDto userInformation = new UserInformationDto(user, fileUrl, rate);

        List<String> images = new LinkedList<>();

        List<StudyFile> studyFileList = studyFileService.findStudyFileByStudy(studyById);
        for (StudyFile studyFile:
             studyFileList) {
            String studyImg = s3Service.getFileUrl(studyFile.getFile());
            images.add(studyImg);
        }

        List<String> give = new LinkedList<>();
        List<String> take = new LinkedList<>();

        List<StudyCategory> studyCategoryList = studyCategoryService.findStudyCategoryByStudy(studyById);
        for (StudyCategory studyCategory:
             studyCategoryList) {
            if (studyCategory.getType() == 1) {
                give.add(studyCategory.getCategory().getName());
            } else {
                take.add(studyCategory.getCategory().getName());
            }
        }

        FindStudyDetailResponse findStudyDetailResponse = new FindStudyDetailResponse(studyById, userInformation, give, take, images);

        return ResFormat.response(true, 200, findStudyDetailResponse);
    }

//    @PostMapping("/apply")
//    public ResponseEntity<Object> applyStudy(HttpServletRequest request, @RequestParam Long id) {
////       예외. 이미 신청한 사람은 신청 못함
//    }
}
