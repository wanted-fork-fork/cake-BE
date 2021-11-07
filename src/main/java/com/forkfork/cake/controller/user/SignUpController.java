package com.forkfork.cake.controller.user;

import com.forkfork.cake.domain.Certification;
import com.forkfork.cake.domain.University;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.dto.request.signup.request.ConfirmCertificationReqeust;
import com.forkfork.cake.dto.request.signup.request.OverlapEmailReqeust;
import com.forkfork.cake.dto.request.signup.request.SendCertificationReqeust;
import com.forkfork.cake.dto.request.signup.request.SignUpRequest;
import com.forkfork.cake.dto.request.signup.response.FindAllUniversityResponse;
import com.forkfork.cake.service.CertificationService;
import com.forkfork.cake.service.MailService;
import com.forkfork.cake.service.UniversityService;
import com.forkfork.cake.service.UserService;
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

        return ResFormat.response(true, 201, "이메일 인증 메일을 발송했습니다.");
    }

    @PostMapping("/certification/confirm")
    public ResponseEntity<Object> confirmCertification(@RequestBody ConfirmCertificationReqeust confirmCertificationReqeust) {
        Certification certificationByEmail = certificationService.findCertificationByEmail(confirmCertificationReqeust.getEmail());

        if (certificationByEmail.getCode().equals(confirmCertificationReqeust.getCode())) {
            return ResFormat.response(true, 200, "인증번호가 일치합니다.");
        } else {
            return ResFormat.response(false, 400, "인증번호가 일치하지 않습니다.");
        }
    }

    @PostMapping("/overlap")
    public ResponseEntity<Object> checkOverlapEmail(@RequestBody OverlapEmailReqeust overlapEmailReqeust) {
        User userByEmail = userService.findUserByEmail(overlapEmailReqeust.getEmail());

        if (userByEmail == null) {
            return ResFormat.response(true, 200, "중복된 이메일이 없습니다.");
        }
        return ResFormat.response(true, 400, "중복된 이메일입니다.");
    }

    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequest signUpRequest) {

        String encodedPwd = passwordEncoder.encode(signUpRequest.getPwd());
        University university = universityService.findUnivById(signUpRequest.getUniv());

        User user = signUpRequest.toEntity(encodedPwd, university);
        userService.saveUser(user);

        return ResFormat.response(true, 201, "유저 생성을 완료했습니다.");

    }


}
