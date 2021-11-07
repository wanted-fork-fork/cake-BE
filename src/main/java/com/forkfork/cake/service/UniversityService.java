package com.forkfork.cake.service;

import com.forkfork.cake.domain.University;
import com.forkfork.cake.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    public List<University> findAllUniversity() {
        return universityRepository.findAll();
    }
}
