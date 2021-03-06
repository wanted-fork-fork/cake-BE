package com.forkfork.cake.controller.user;

import com.forkfork.cake.domain.*;
import com.forkfork.cake.dto.signup.request.*;
import com.forkfork.cake.dto.signup.response.FindAllUniversityResponse;
import com.forkfork.cake.service.*;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final UniversityService universityService;
    private final MailService mailService;
    private final CertificationService certificationService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserCategoryService userCategoryService;
    private final CategoryService categoryService;
    private final ConsentFormService consentFormService;


    @GetMapping("/univ")
    public ResponseEntity<Object> findAllUniversity() {
        List<University> allUniversity = universityService.findAllUniversity();

        List<FindAllUniversityResponse> responseList = new LinkedList<>();

        for (University university:
             allUniversity) {
            responseList.add(new FindAllUniversityResponse(university.getId(), university.getName(), university.getEmail()));
        }

        return ResFormat.response(true, 200, responseList);
    }

    @PostMapping("/certification")
    @Transactional
    public ResponseEntity<Object> sendCertification(@RequestBody SendCertificationReqeust sendCertificationReqeust) throws MessagingException {
        String email = sendCertificationReqeust.getEmail();
        String code = mailService.sendCertificationCodeMail(email);

        Certification certification = Certification.builder().code(code).email(email).build();

        certificationService.deleteCertificationByEmail(email);
        certificationService.saveCertification(certification);

        return ResFormat.response(true, 201, "????????? ?????? ????????? ??????????????????.");
    }

    @PostMapping("/certification/confirm")
    public ResponseEntity<Object> confirmCertification(@RequestBody ConfirmCertificationReqeust confirmCertificationReqeust) {
        Certification certificationByEmail = certificationService.findCertificationByEmail(confirmCertificationReqeust.getEmail());

        if (certificationByEmail.getCode().equals(confirmCertificationReqeust.getCode())) {
            return ResFormat.response(true, 200, "??????????????? ???????????????.");
        } else {
            return ResFormat.response(false, 400, "??????????????? ???????????? ????????????.");
        }
    }

    @PostMapping("/overlap/email")
    public ResponseEntity<Object> checkOverlapEmail(@RequestBody OverlapEmailReqeust overlapEmailReqeust) {
        User userByEmail = userService.findUserByEmail(overlapEmailReqeust.getEmail());

        if (userByEmail == null) {
            return ResFormat.response(true, 200, "????????? ???????????? ????????????.");
        }
        return ResFormat.response(true, 400, "????????? ??????????????????.");
    }

    @PostMapping("/overlap/nickname")
    public ResponseEntity<Object> checkOverlapNickname(@RequestBody OverlapNicknameReqeust overlapNicknameReqeust) {
        User userByNickname = userService.findUserByNickname(overlapNicknameReqeust.getNickname());

        if (userByNickname == null) {
            return ResFormat.response(true, 200, "????????? ???????????? ????????????.");
        }
        return ResFormat.response(true, 400, "????????? ??????????????????.");
    }

    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequest signUpRequest) {

        String encodedPwd = passwordEncoder.encode(signUpRequest.getPwd());
        University university = universityService.findUnivById(signUpRequest.getUniv());

        User user = signUpRequest.toUserEntity(encodedPwd, university);

        for (Long giveId:
             signUpRequest.getGive()) {
            Category categoryById = categoryService.findCategoryById(giveId);
            UserCategory userCategory = UserCategory.builder().category(categoryById).user(user).type(1).build();

            user.addUserCategory(userCategory);
        }

        for (Long takeId:
                signUpRequest.getTake()) {
            Category categoryById = categoryService.findCategoryById(takeId);
            UserCategory userCategory = UserCategory.builder().category(categoryById).user(user).type(2).build();

            user.addUserCategory(userCategory);
        }

        User saveUser = userService.saveUser(user);

        ConsentForm consentForm = ConsentForm.builder().serviceRule(true).personalInfo(true).user(saveUser).build();
        consentFormService.saveConsentForm(consentForm);

        return ResFormat.response(true, 201, "?????? ????????? ??????????????????.");

    }


}
