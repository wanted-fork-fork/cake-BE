package com.forkfork.cake.service;

import com.forkfork.cake.domain.User;
import com.forkfork.cake.domain.UserCategory;
import com.forkfork.cake.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;

    public UserCategory saveUserCategory(UserCategory category) {
        return userCategoryRepository.save(category);
    }

    public List<UserCategory> findUserCategoryByUser(User userByEmail) {

        return userCategoryRepository.findAllByUser(userByEmail);
    }
}
