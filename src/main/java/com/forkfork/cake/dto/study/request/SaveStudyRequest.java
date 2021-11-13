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

    private String location;

    private Long peopleCnt;

    private String chatRoom;

    private String roomPwd;

    private List<String> images;

    private List<Long> give;

    private List<Long> take;

    public Study toStudyEntity(User user, String pwd) {
        return Study.builder()
                .user(user)
                .type(this.type)
                .content(this.content)
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .location(this.location)
                .peopleCnt(this.peopleCnt)
                .earlyClosing(false)
                .chatRoom(this.chatRoom)
                .roomPwd(pwd)
                .build();
    }

}