package com.forkfork.cake.service;

import com.forkfork.cake.domain.UserCategory;
import com.forkfork.cake.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;

    public UserCategory saveUserCategory(UserCategory category) {
        return userCategoryRepository.save(category);
    }
}
