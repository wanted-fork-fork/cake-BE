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

    public Double findUserRate(User user) {
        Double rate = null;
        List<Review> allByToUser = reviewRepository.findAllByToUser(user);
        Long cnt = 0L;
        Double point = 0D;
        for (Review review :
                allByToUser) {
            cnt += 1;
            point += review.getReviewPoint();
        }

        if (cnt >= 5) {
            rate = point / cnt;
        }

        return rate;
    }
}
