package com.forkfork.cake.dto.auth.request;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String pwd;
}
