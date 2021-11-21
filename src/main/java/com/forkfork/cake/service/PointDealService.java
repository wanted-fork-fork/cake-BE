package com.forkfork.cake.service;

import com.forkfork.cake.domain.PointDeal;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.repository.PointDealRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointDealService {

    private final PointDealRespository pointDealRespository;
    private final UserService userService;

    public PointDeal savePointDeal(PointDeal deal) {
        return pointDealRespository.save(deal);
    }

    public void makePointDeal(User toUser, User fromUser, Long point) {
        PointDeal deal = PointDeal.builder().point(point).fromUser(fromUser).toUser(toUser).build();
        pointDealRespository.save(deal);
        fromUser.reducePoint(point);
        toUser.appendPoint(point);

        userService.saveUser(toUser);
        userService.saveUser(fromUser);
    }
}
