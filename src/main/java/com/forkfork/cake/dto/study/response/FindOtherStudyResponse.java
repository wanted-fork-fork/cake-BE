package com.forkfork.cake.dto.study.response;

import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyMember;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindOtherStudyResponse {
    Long id;
    String title;
    Long peopleCnt;
    Date startDate;
    Date endDate;
    List<String> give;
    List<String> take;
    String img;
    int state;
    UserInformationDto userInformationDto;

    public FindOtherStudyResponse(Study study, List<String> give, List<String> take, String img) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.peopleCnt = study.getPeopleCnt();
        this.startDate = study.getStartDate();
        this.endDate = study.getEndDate();
    }

    public void updateUserInfo(UserInformationDto userInformationDto) {
        this.userInformationDto = userInformationDto;
    }
    public void updateMyType(StudyMember studyMember) {
        this.state = studyMember.getState();
    }
}
