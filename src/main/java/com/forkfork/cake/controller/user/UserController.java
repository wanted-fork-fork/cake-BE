package com.forkfork.cake.controller.user;


import com.forkfork.cake.domain.*;
import com.forkfork.cake.dto.user.response.FindMyPageResponse;
import com.forkfork.cake.dto.util.CategoryDto;
import com.forkfork.cake.service.*;
import com.forkfork.cake.util.JwtTokenUtil;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserCategoryService userCategoryService;
    private final ReviewService reviewService;
    private final S3Service s3Service;
    private final StudyMemberService studyMemberService;

    @GetMapping("/univ")
    public ResponseEntity<Object> findMyUniv(HttpServletRequest request) {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);
        University university = userByEmail.getUniversity();

        return ResFormat.response(true, 200, university.getName());
    }

    @GetMapping("/mypage")
    public ResponseEntity<Object> findMyPage(HttpServletRequest request) {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);

        List<UserCategory> userCategoryByUser = userCategoryService.findUserCategoryByUser(userByEmail);

        List<CategoryDto> give = new LinkedList<>();
        List<CategoryDto> take = new LinkedList<>();
        for (UserCategory userCategory:
                userCategoryByUser) {
            Category category = userCategory.getCategory();

            if (userCategory.getType() == 1) {
                give.add(new CategoryDto(category.getId(), category.getName(), category.getImg()));
            } else {
                take.add(new CategoryDto(category.getId(), category.getName(), category.getImg()));
            }
        }

        Double userRate = reviewService.findUserRate(userByEmail);

        String profileImg = s3Service.getFileUrl(userByEmail.getImg());

        List<StudyMember> studyMemberByUser = studyMemberService.findStudyMemberByUser(userByEmail);
        Long studyCnt = 0L;

        for (StudyMember studyMember:
             studyMemberByUser) {
            if (studyMember.getState() == 1 || studyMember.getState() == 3) {
                studyCnt += 1;
            }
        }

        FindMyPageResponse findMyPageResponse = new FindMyPageResponse(userByEmail, userRate, profileImg, give, take, studyCnt);

        return ResFormat.response(true, 200, findMyPageResponse);
    }

    @GetMapping("/point")
    public ResponseEntity<Object> findMyRemainPoint(HttpServletRequest request) {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);

        return ResFormat.response(true, 200, userByEmail.getPoint());
    }
}
