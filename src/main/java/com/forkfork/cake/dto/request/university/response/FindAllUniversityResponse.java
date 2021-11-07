package com.forkfork.cake.dto.request.university.response;

import lombok.Data;

@Data
public class FindAllUniversityResponse {
    private String name;
    private String email;

    public FindAllUniversityResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
