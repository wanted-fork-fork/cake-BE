package com.forkfork.cake.controller.user;


import com.forkfork.cake.domain.University;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.service.UniversityService;
import com.forkfork.cake.service.UserService;
import com.forkfork.cake.util.JwtTokenUtil;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UniversityService universityService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/univ")
    public ResponseEntity<Object> findMyUniv(HttpServletRequest request) {
        String email = jwtTokenUtil.getSubject(request);
        User userByEmail = userService.findUserByEmail(email);
        University university = userByEmail.getUniversity();

        return ResFormat.response(true, 200, university.getName());
    }

}
