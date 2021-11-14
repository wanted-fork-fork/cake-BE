package com.forkfork.cake.dto.study.response;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyMember;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindMyStudyResponse {
    Long id;
    String title;
    Long peopleCnt;
    Date startDate;
    Date endDate;
    List<String> give;
    List<String> take;
    String img;
    int state;

    public FindMyStudyResponse(Study study, List<String> give, List<String> take, String img) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.peopleCnt = study.getPeopleCnt();
        this.startDate = study.getStartDate();
        this.endDate = study.getEndDate();
        this.give = give;
        this.take = take;
        this.img = img;
    }

    public void updateMyType(StudyMember studyMember) {
        this.state = studyMember.getState();
    }
}
