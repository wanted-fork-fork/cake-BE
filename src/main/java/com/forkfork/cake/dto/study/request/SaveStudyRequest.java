package com.forkfork.cake.dto.study.request;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SaveStudyRequest {
    private String title;

    private String content;

    private int type;

    private Date startDate;

    private Date endDate;

    private String storeName;

    private String storeAddress;

    private Long peopleCnt;

    private List<String> images;

    private List<Long> give;

    private List<Long> take;

    private Long point;

    public Study toStudyEntity(User user) {
        return Study.builder()
                .user(user)
                .type(this.type)
                .content(this.content)
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .storeAddress(this.storeAddress)
                .storeName(this.storeName)
                .peopleCnt(this.peopleCnt)
                .state(1)
                .point(this.point)
                .build();
    }

}
