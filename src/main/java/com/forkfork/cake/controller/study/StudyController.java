package com.forkfork.cake.controller.study;

import com.forkfork.cake.domain.*;
import com.forkfork.cake.dto.study.request.SaveStudyRequest;
import com.forkfork.cake.service.CategoryService;
import com.forkfork.cake.service.StudyService;
import com.forkfork.cake.service.UserService;
import com.forkfork.cake.util.AES128Encoder;
import com.forkfork.cake.util.JwtTokenUtil;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final CategoryService categoryService;


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

        return ResFormat.response(true, 201, "스터디 생성을 완료했습니다.");

    }

}
