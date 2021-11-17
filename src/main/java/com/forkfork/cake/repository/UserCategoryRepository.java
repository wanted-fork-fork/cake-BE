package com.forkfork.cake.repository;

import com.forkfork.cake.domain.User;
import com.forkfork.cake.domain.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    List<UserCategory> findAllByUser(User userByEmail);

}
