package com.forkfork.cake.repository;

import com.forkfork.cake.domain.Review;
import com.forkfork.cake.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByToUser(User user);
}
