package com.forkfork.cake.repository;

import com.forkfork.cake.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByNickname(String nickname);
}
