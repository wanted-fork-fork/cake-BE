package com.forkfork.cake.controller;

import com.forkfork.cake.service.AuthService;
import com.forkfork.cake.util.JwtTokenUtil;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class RootController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public String main() {
        return "index page";
    }

//    @PostMapping("login")
//    @Transactional
//    public ResponseEntity login(@RequestBody LoginTestRequest loginTestRequest, HttpServletResponse response) {
//
////        로그인 실패
//        if (!(loginTestRequest.getEmail().equals("test@ajou.ac.kr") && loginTestRequest.getPwd().equals("a123"))) {
//            return ResFormat.response(false, 400, "아이디 혹은 비밀번호가 유효하지 않습니다.");
//        }
//
//        String accessToken = authService.createToken(loginTestRequest.getEmail(), 1 * 60 * 1000L);
//        String refreshToken = authService.createToken(loginTestRequest.getEmail(), 30 * 24 * 60 * 60 * 1000L);
//        authService.deleteAuthByEmail(loginTestRequest.getEmail());
//        Auth newAuth = authService.createAuth(accessToken, refreshToken, loginTestRequest.getEmail());
//        authService.saveAuth(newAuth);
//
////        // create a cookie
////        Cookie cookie = new Cookie("refreshToken", refreshToken);
////
////        // expires in 30 days
////        cookie.setMaxAge(30 * 24 * 60 * 60);
////
////        // optional properties
////        cookie.setHttpOnly(true);
////        cookie.setPath("/refresh");
//        // add cookie to response
////        response.addCookie(cookie);
//
//        String refreshCookie = "refreshToken="+ refreshToken +"; Max-Age=2592000; Path=/; HttpOnly; SameSite=None; secure;";
//        response.setHeader("Set-Cookie", refreshCookie);
//
//        return ResFormat.response(true, 201, accessToken);
//    }
//
//    @PostMapping("/refresh")
//    @Transactional
//    public ResponseEntity refresh(HttpServletRequest request, HttpServletResponse response) {
//        Cookie[] cookies = request.getCookies();
//
//        String refreshToken = "";
//        for (Cookie cookie :
//                cookies) {
//            if (cookie.getName().equals("refreshToken")) {
//                refreshToken = cookie.getValue();
//            }
//        }
//
//        if (refreshToken.equals("")) {
//            return ResFormat.response(false, 401, "refreshToken이 없습니다.");
//        }
//
//        Auth authByRefreshToken = authService.findAuthByRefreshToken(refreshToken);
//
//        if (authByRefreshToken == null) {
//            return ResFormat.response(false, 401, "DB에 해당 refreshToken이 존재하지 않습니다.");
//        }
//
//        String email = authByRefreshToken.getEmail();
//
//        if (!authByRefreshToken.getRefreshToken().equals(refreshToken)) {
//            return ResFormat.response(false, 401, "다른 기기에서 로그인 됐었습니다.");
//        }
//        String accessToken = authService.createToken(email, 1 * 60 * 1000L);
//        String newRefreshToken = authService.createToken(email, 30 * 24 * 60 * 60 * 1000L);
//        authService.deleteAuthByEmail(email);
//        Auth newAuth = authService.createAuth(accessToken, newRefreshToken, email);
//        authService.saveAuth(newAuth);
//
////        // create a cookie
////        Cookie cookie = new Cookie("refreshToken", refreshToken);
////
////        // expires in 30 days
////        cookie.setMaxAge(30 * 24 * 60 * 60);
////
////        // optional properties
////        cookie.setHttpOnly(true);
////        cookie.setPath("/refresh");
////
////        // add cookie to response
////        response.addCookie(cookie);
//
//        String refreshCookie = "refreshToken="+ newRefreshToken +"; Max-Age=2592000; Path=/; HttpOnly; SameSite=None; secure;";
//        response.setHeader("Set-Cookie", refreshCookie);
//
//        return ResFormat.response(true, 201, accessToken);
//    }
//
    @GetMapping("/test")
    public ResponseEntity<Object> test(HttpServletRequest request) {
        String uid = jwtTokenUtil.getSubject(request);

        return ResFormat.response(true, 200, uid);
    }
}
