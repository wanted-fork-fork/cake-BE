package com.forkfork.cake.controller.study;

import com.forkfork.cake.domain.*;
import com.forkfork.cake.dto.category.SeperateCategoryDto;
import com.forkfork.cake.dto.study.request.ApplyStudyRequest;
import com.forkfork.cake.dto.study.request.SaveStudyRequest;
import com.forkfork.cake.dto.study.response.*;
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

        for (String img :
                saveStudyRequest.getImages()) {
            StudyFile build = StudyFile.builder().file(img).study(study).build();
            study.addStudyFile(build);
        }

        for (Long give :
                saveStudyRequest.getGive()) {
            Category categoryById = categoryService.findCategoryById(give);
            StudyCategory build = StudyCategory.builder().category(categoryById).study(study).type(1).build();
            study.addStudyCategory(build);
        }

        for (Long take :
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
    public ResponseEntity<Object> findStudyDetail(HttpServletRequest request, @RequestParam Long id) {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);

        Study studyById = studyService.findStudyById(id);
        User user = studyById.getUser();

        String profileUrl = null;
        if (user.getImg() != null) {
            profileUrl = s3Service.getFileUrl(user.getImg());
        }

        Double rate = reviewService.findUserRate(user);

        UserInformationDto userInformation = new UserInformationDto(user, profileUrl, rate);

        List<String> images = new LinkedList<>();

        List<StudyFile> studyFileList = studyFileService.findStudyFileByStudy(studyById);
        for (StudyFile studyFile :
                studyFileList) {
            String studyImg = s3Service.getFileUrl(studyFile.getFile());
            images.add(studyImg);
        }

        List<StudyCategory> studyCategoryList = studyCategoryService.findStudyCategoryByStudy(studyById);
        SeperateCategoryDto seperateCategoryDto = studyCategoryService.seperateCategory(studyCategoryList);

        Boolean apply = true;

        List<StudyMember> studyMemberByStudy = studyMemberService.findStudyMemberByStudy(studyById);

        for (StudyMember studyMember :
                studyMemberByStudy) {
            if (studyMember.getUser().getEmail().equals(userByEmail.getEmail())) {
                apply = false;
            }
        }

        FindStudyDetailResponse findStudyDetailResponse = new FindStudyDetailResponse(studyById, userInformation, seperateCategoryDto.getGive(), seperateCategoryDto.getTake(), images, apply);

        return ResFormat.response(true, 200, findStudyDetailResponse);
    }

    @PostMapping("/apply")
    public ResponseEntity<Object> applyStudy(HttpServletRequest request, @RequestBody ApplyStudyRequest applyStudyRequest) {
//       예외. 이미 신청한 사람은 신청 못함
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);

        Study studyById = studyService.findStudyById(applyStudyRequest.getId());

        StudyMember studyMember = StudyMember.builder().study(studyById).user(userByEmail).state(2).msg(applyStudyRequest.getContent()).build();

        for (String img :
                applyStudyRequest.getImages()) {
            ApplyFile applyFile = ApplyFile.builder().file(img).studyMember(studyMember).build();
            studyMember.addApplyFile(applyFile);
        }

        studyMemberService.saveStudyMember(studyMember);

        return ResFormat.response(true, 201, "참여 신청을 완료했습니다.");
    }

    @GetMapping("/myStudy/mine")
    public ResponseEntity<Object> findMyStudy(HttpServletRequest request) {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);

        List<StudyMember> studyMemberByUserAndType = studyMemberService.findStudyMemberByUserAndState(userByEmail, 1);

        List<FindMyStudyResponse> findMyStudyResponses = new LinkedList<>();

        for (StudyMember studyMember:
                studyMemberByUserAndType) {

            Study study = studyMember.getStudy();
            List<StudyCategory> studyCategoryByStudy = studyCategoryService.findStudyCategoryByStudy(study);
            SeperateCategoryDto seperateCategoryDto = studyCategoryService.seperateCategory(studyCategoryByStudy);

            String img = studyFileService.findThumbnailImg(study);
            if (img == null) {
                img = studyFileService.findThumbnailWithTakeSize(seperateCategoryDto.getTake().size());
            }
            FindMyStudyResponse findMyStudy = new FindMyStudyResponse(study, seperateCategoryDto.getGive(), seperateCategoryDto.getTake(), img);
            findMyStudy.updateMyType(studyMember);
            findMyStudyResponses.add(findMyStudy);
        }

        return ResFormat.response(true, 200, findMyStudyResponses);
    }

    @GetMapping("/myStudy/other")
    public ResponseEntity<Object> findOtherStudy(HttpServletRequest request) {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);

        List<StudyMember> studyMemberList = new LinkedList<>();

        for (int i = 2; i < 5; i++) {
        List<StudyMember> studyMembers = studyMemberService.findStudyMemberByUserAndState(userByEmail, i);
        studyMemberList.addAll(studyMembers);
        }

        List<FindOtherStudyResponse> findMyStudyResponses = new LinkedList<>();

        for (StudyMember studyMember:
                studyMemberList) {


            Study study = studyMember.getStudy();
            List<StudyCategory> studyCategoryByStudy = studyCategoryService.findStudyCategoryByStudy(study);

            SeperateCategoryDto seperateCategoryDto = studyCategoryService.seperateCategory(studyCategoryByStudy);

            String img = studyFileService.findThumbnailImg(study);
            if (img == null) {
                img = studyFileService.findThumbnailWithTakeSize(seperateCategoryDto.getTake().size());
            }

            User ownerUser = study.getUser();
            String profileUrl = null;
            if (ownerUser.getImg() != null) {
                profileUrl = s3Service.getFileUrl(ownerUser.getImg());
            }

            Double rate = reviewService.findUserRate(ownerUser);

            UserInformationDto userInformationDto = new UserInformationDto(ownerUser, profileUrl, rate);

            FindOtherStudyResponse findMyStudy = new FindOtherStudyResponse(study, seperateCategoryDto.getGive(), seperateCategoryDto.getTake(), img);
            findMyStudy.updateMyType(studyMember);
            findMyStudy.updateUserInfo(userInformationDto);
            findMyStudyResponses.add(findMyStudy);
        }

        return ResFormat.response(true, 200, findMyStudyResponses);
    }

    @GetMapping("/chat")
    public ResponseEntity<Object> findStudyChatInfo(HttpServletRequest request, @RequestParam Long studyId) throws Exception {
        Study studyById = studyService.findStudyById(studyId);

        String chatRoom = studyById.getChatRoom();
        String roomPwd = studyById.getRoomPwd();

        AES128Encoder aes128Encoder = new AES128Encoder(AES128KEY);
        String decrypt = aes128Encoder.decrypt(roomPwd);

        FindStudyChatInfoResponse findStudyChatInfoResponse = new FindStudyChatInfoResponse(chatRoom, decrypt);

        return ResFormat.response(true, 200, findStudyChatInfoResponse);

    }

}
