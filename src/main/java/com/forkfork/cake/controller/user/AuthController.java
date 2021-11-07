package com.forkfork.cake.controller.user;

import com.forkfork.cake.domain.Auth;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.dto.auth.request.LoginRequest;
import com.forkfork.cake.service.AuthService;
import com.forkfork.cake.service.UserService;
import com.forkfork.cake.util.JwtTokenUtil;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    @Transactional
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        User userByEmail = userService.findUserByEmail(loginRequest.getEmail());

        if (userByEmail == null) {
            return ResFormat.response(false, 400, "이메일이 존재하지 않습니다.");
        }

//        로그인 실패
        if (!(passwordEncoder.matches(loginRequest.getPwd(), userByEmail.getPwd()))) {
            return ResFormat.response(false, 400, "비밀번호가 올바르지 않습니다.");
        }

        String accessToken = authService.createToken(userByEmail.getEmail(), 2 * 60 * 60 * 1000L);
        String refreshToken = authService.createToken(userByEmail.getEmail(), 30 * 24 * 60 * 60 * 1000L);
        authService.deleteAuthByUser(userByEmail);

        Auth newAuth = authService.createAuth(accessToken, refreshToken, userByEmail);
        authService.saveAuth(newAuth);


        String refreshCookie = "refreshToken=" + refreshToken + "; Max-Age=2592000; Path=/; HttpOnly; SameSite=None; secure;";
        response.setHeader("Set-Cookie", refreshCookie);

        return ResFormat.response(true, 201, accessToken);
    }

    @PostMapping("/refresh")
    @Transactional
    public ResponseEntity<Object> refresh(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        String refreshToken = "";
        for (Cookie cookie :
                cookies) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken.equals("")) {
            return ResFormat.response(false, 401, "refreshToken이 없습니다.");
        }

        Auth authByRefreshToken = authService.findAuthByRefreshToken(refreshToken);

        if (authByRefreshToken == null) {
            return ResFormat.response(false, 401, "DB에 해당 refreshToken이 존재하지 않습니다.");
        }

        User user = authByRefreshToken.getUser();

        if (!authByRefreshToken.getRefreshToken().equals(refreshToken)) {
            return ResFormat.response(false, 401, "다른 기기에서 로그인 됐었습니다.");
        }
        String accessToken = authService.createToken(user.getEmail(), 2 * 60 * 60 * 1000L);
        String newRefreshToken = authService.createToken(user.getEmail(), 30 * 24 * 60 * 60 * 1000L);

        authService.deleteAuthByUser(user);
        Auth newAuth = authService.createAuth(accessToken, newRefreshToken, user);
        authService.saveAuth(newAuth);

        String refreshCookie = "refreshToken=" + newRefreshToken + "; Max-Age=2592000; Path=/; HttpOnly; SameSite=None; secure;";
        response.setHeader("Set-Cookie", refreshCookie);

        return ResFormat.response(true, 201, accessToken);
    }

}
