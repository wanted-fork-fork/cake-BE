package com.forkfork.cake.dto.studyMember.response;

import com.forkfork.cake.domain.StudyMember;
import com.forkfork.cake.domain.User;
import lombok.Data;

@Data
public class FindAllStudyMemberResponse {
    // 이름, 평점, 참석 여부, 신청서, id, 프로필

    Long id;
    String nickname;
    String profileImg;
    Double rate;
    int state;
    String msg;

    public FindAllStudyMemberResponse(StudyMember studyMember, User user, String profileImg, Double rate) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.profileImg = profileImg;
        this.rate = rate;
        this.state = studyMember.getState();
        this.msg = studyMember.getMsg();
    }
}
