package com.forkfork.cake.util;

import com.forkfork.cake.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final AuthService authService;

    public String getSubject(HttpServletRequest request) {
        String uid = (String) request.getAttribute("uid");
        if (uid != null) {
            return uid;
        } else {
            String authorization = request.getHeader("Authorization");
            String[] bearer_s = authorization.split("Bearer ");
            String accessToken = bearer_s[1];

            Claims claimsByToken = authService.getClaimsByToken(accessToken);
            return authService.getSubject(claimsByToken);
        }
    }

    public String getSubject(String accessToken) {
        Claims claimsByToken = authService.getClaimsByToken(accessToken);
        return authService.getSubject(claimsByToken);
    }
}
