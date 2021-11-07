package com.forkfork.cake.dto.request.signup.request;

import lombok.Data;

@Data
public class ConfirmCertificationReqeust {
    String email;
    String code;
}
