package com.forkfork.cake.controller;

import com.forkfork.cake.domain.University;
import com.forkfork.cake.dto.request.university.response.FindAllUniversityResponse;
import com.forkfork.cake.service.UniversityService;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final UniversityService universityService;

    @GetMapping("/univ")
    public ResponseEntity<Object> findAllUniversity() {
        List<University> allUniversity = universityService.findAllUniversity();

        List<FindAllUniversityResponse> responseList = new LinkedList<>();

        for (University university:
             allUniversity) {
            responseList.add(new FindAllUniversityResponse(university.getName(), university.getEmail()));
        }

        return ResFormat.response(true, 200, responseList);
    }


}
