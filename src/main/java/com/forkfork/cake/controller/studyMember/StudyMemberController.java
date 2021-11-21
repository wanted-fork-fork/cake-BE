package com.forkfork.cake.controller.studyMember;

import com.forkfork.cake.domain.ApplyFile;
import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyMember;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.dto.study.response.UserInformationDto;
import com.forkfork.cake.dto.studyMember.request.ApprovalStudyMemberRequest;
import com.forkfork.cake.dto.studyMember.response.FindAllStudyMemberResponse;
import com.forkfork.cake.dto.studyMember.response.FindStudyMemberDetailResponse;
import com.forkfork.cake.service.*;
import com.forkfork.cake.util.JwtTokenUtil;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studymember")
public class StudyMemberController {

    private final StudyMemberService studyMemberService;
    private final StudyService studyService;
    private final S3Service s3Service;
    private final ReviewService reviewService;
    private final ApplyFileService applyFileService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Object> findAllStudyMembers(@RequestParam Long studyId) {
        Study studyById = studyService.findStudyById(studyId);
        List<StudyMember> studyMemberByStudy = studyMemberService.findStudyMemberByStudy(studyById);

        List<FindAllStudyMemberResponse> memberResponses = new LinkedList<>();

        for (StudyMember studyMember:
             studyMemberByStudy) {
            if (studyMember.getState() != 1) {
                User user = studyMember.getUser();
                String profileImg = s3Service.getFileUrl(user.getImg());

                Double userRate = reviewService.findUserRate(user);

                FindAllStudyMemberResponse findAllStudyMemberResponse = new FindAllStudyMemberResponse(studyMember, user, profileImg, userRate);

                memberResponses.add(findAllStudyMemberResponse);
            }
        }

        return ResFormat.response(true, 200, memberResponses);
    }

    @GetMapping("/detail")
    public ResponseEntity<Object> findStudyMemberDetail(@RequestParam Long studyMemberId) {
        StudyMember studyMemberById = studyMemberService.findStudyMemberById(studyMemberId);
        List<ApplyFile> allApplyFileByStudyMember = applyFileService.findAllApplyFileByStudyMember(studyMemberById);

        List<String> applyFiles = new LinkedList<>();
        for (ApplyFile applyFile:
                allApplyFileByStudyMember) {
            String fileUrl = s3Service.getFileUrl(applyFile.getFile());
            applyFiles.add(fileUrl);
        }

        User user = studyMemberById.getUser();
        String fileUrl = s3Service.getFileUrl(user.getImg());

        Double userRate = reviewService.findUserRate(user);

        FindStudyMemberDetailResponse findStudyMemberDetailResponse = new FindStudyMemberDetailResponse(studyMemberById, applyFiles, userRate, fileUrl);

        return ResFormat.response(true, 200, findStudyMemberDetailResponse);
    }

    @PostMapping("/approval")
    public ResponseEntity<Object> approvalStudyMember(@RequestBody ApprovalStudyMemberRequest approvalStudyMemberRequest) {
        StudyMember studyMemberById = studyMemberService.findStudyMemberById(approvalStudyMemberRequest.getStudyMemberId());
        studyMemberById.updateState(approvalStudyMemberRequest.getState());

        studyMemberService.saveStudyMember(studyMemberById);

        return ResFormat.response(true, 201, "신청자의 상태를 변경했습니다.");

    }

    @DeleteMapping("/cancel")
    public ResponseEntity<Object> cancleApplication(HttpServletRequest request, @RequestParam Long studyId) {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);
        Study studyById = studyService.findStudyById(studyId);

        StudyMember studyMemberByUserAndStudy = studyMemberService.findStudyMemberByUserAndStudy(userByEmail, studyById);
        List<ApplyFile> allApplyFileByStudyMember = applyFileService.findAllApplyFileByStudyMember(studyMemberByUserAndStudy);

        for (ApplyFile applyFile:
             allApplyFileByStudyMember) {
            s3Service.deleteFile(applyFile.getFile());
        }

        studyMemberService.deleteByStudyMember(studyMemberByUserAndStudy);

        return ResFormat.response(true, 201, "유저의 스터디 참여 신청이 취소됐습니다.");
    }
}
