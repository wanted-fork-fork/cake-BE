package com.forkfork.cake.service;

import com.forkfork.cake.domain.Review;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> findAllReviewByToUser(User user) {
        return reviewRepository.findAllByToUser(user);
    }
}
