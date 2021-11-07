package com.forkfork.cake.dto.request.signup.response;

import lombok.Data;

@Data
public class FindAllUniversityResponse {
    private Long id;
    private String name;
    private String email;

    public FindAllUniversityResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
