package com.forkfork.cake.controller;

import com.forkfork.cake.domain.Auth;
import com.forkfork.cake.dto.request.LoginTestRequest;
import com.forkfork.cake.service.AuthService;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final AuthService authService;

    @PostMapping("login")
    @Transactional
    public ResponseEntity login(@RequestBody LoginTestRequest loginTestRequest, HttpServletResponse response) {

//        로그인 실패
        if (!(loginTestRequest.getEmail().equals("test@ajou.ac.kr") && loginTestRequest.getPwd().equals("a123"))) {
            return ResFormat.response(false, 400, "아이디 혹은 비밀번호가 유효하지 않습니다.");
        }

        String accessToken = authService.createToken(loginTestRequest.getEmail(), 1 * 60 * 1000L);
        String refreshToken = authService.createToken(loginTestRequest.getEmail(), 30 * 24 * 60 * 60 * 1000L);
        authService.deleteAuthByEmail(loginTestRequest.getEmail());
        Auth newAuth = authService.createAuth(accessToken, refreshToken, loginTestRequest.getEmail());
        authService.saveAuth(newAuth);

        // create a cookie
        Cookie cookie = new Cookie("refreshToken",refreshToken);

        // expires in 30 days
        cookie.setMaxAge(30 * 24 * 60 * 60);

        // optional properties
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/refresh");

        // add cookie to response
        response.addCookie(cookie);


        return ResFormat.response(true, 201, accessToken);
    }
}
