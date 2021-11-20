package com.forkfork.cake.controller.studyMember;

import com.forkfork.cake.domain.ApplyFile;
import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyMember;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.dto.studyMember.request.ApprovalStudyMemberRequest;
import com.forkfork.cake.dto.studyMember.response.FindAllStudyMemberResponse;
import com.forkfork.cake.dto.studyMember.response.FindStudyMemberDetailResponse;
import com.forkfork.cake.service.*;
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

        FindStudyMemberDetailResponse findStudyMemberDetailResponse = new FindStudyMemberDetailResponse(studyMemberById, applyFiles);

        return ResFormat.response(true, 200, findStudyMemberDetailResponse);
    }

    @PostMapping("/approval")
    public ResponseEntity<Object> approvalStudyMember(@RequestBody ApprovalStudyMemberRequest approvalStudyMemberRequest) {
        StudyMember studyMemberById = studyMemberService.findStudyMemberById(approvalStudyMemberRequest.getStudyMemberId());
        studyMemberById.updateState(approvalStudyMemberRequest.getState());

        studyMemberService.saveStudyMember(studyMemberById);

        return ResFormat.response(true, 201, "신청자의 상태를 변경했습니다.");

    }
}
