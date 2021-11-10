package com.forkfork.cake.service;

import com.forkfork.cake.domain.User;
import com.forkfork.cake.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }
}
