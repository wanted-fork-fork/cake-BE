package com.forkfork.cake.service;

import com.forkfork.cake.domain.PointDeal;
import com.forkfork.cake.repository.PointDealRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointDealService {

    private final PointDealRespository pointDealRespository;

    public PointDeal savePointDeal(PointDeal deal) {

        return pointDealRespository.save(deal);
    }
}
