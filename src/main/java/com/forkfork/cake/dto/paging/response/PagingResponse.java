package com.forkfork.cake.dto.paging.response;

import com.forkfork.cake.domain.Category;
import com.forkfork.cake.domain.Study;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PagingResponse {
    Long id;
    String title;
    Long peopleCnt;
    Date startDate;
    Date endDate;
    List<String> give;
    List<String> take;
    // null이면 카테고리 사진
    String img;

    public PagingResponse(Study study, String img, List<String> give, List<String> take) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.peopleCnt = study.getPeopleCnt();
        this.startDate = study.getStartDate();
        this.endDate = study.getEndDate();
        this.give = give;
        this.take = take;
        this.img = img;
    }
}
