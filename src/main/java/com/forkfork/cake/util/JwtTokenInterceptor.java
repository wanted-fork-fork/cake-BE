package com.forkfork.cake.util;

import com.forkfork.cake.domain.Auth;
import com.forkfork.cake.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public JwtTokenInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String method = request.getMethod();

        if(method.equals("OPTIONS")){
            return true;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization == null) {
            response = falseSetting(response);
            return false;
        }

        String[] bearer_s = authorization.split("Bearer ");
        String accessToken = bearer_s[1];


        if (accessToken != null) {
            if (authService.isValidToken(accessToken)) {
                return true;
            }else {
                response = falseSetting(response);
                return false;
            }
        } else {
            response = falseSetting(response);
            return false;
        }
    }

    private HttpServletResponse falseSetting(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().println("{\"success\":false,\"status\":401,\"data\":\"Unauthorized Token\"}");

        return response;
    }
}
