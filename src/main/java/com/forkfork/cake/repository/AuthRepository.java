package com.forkfork.cake.repository;

import com.forkfork.cake.domain.Auth;
import com.forkfork.cake.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    void deleteByRefreshToken(String refreshToken);

    Auth findByRefreshToken(String refreshToken);

    Auth findByAccessToken(String accessToken);

    void deleteByUser(User user);
}
