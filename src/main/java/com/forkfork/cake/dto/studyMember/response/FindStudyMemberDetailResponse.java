package com.forkfork.cake.dto.studyMember.response;

import com.forkfork.cake.domain.StudyMember;
import lombok.Data;

import java.util.List;

@Data
public class FindStudyMemberDetailResponse {
    Long studyMemberId;
    String msg;
    List<String> applyFiles;
    Long userId;
    String nickname;
    String profileImg;
    int state;
    Double rate;

    public FindStudyMemberDetailResponse(StudyMember studyMemberById, List<String> applyFiles, Double rate, String profileImg) {
        this.studyMemberId = studyMemberById.getId();
        this.msg = studyMemberById.getMsg();
        this.applyFiles = applyFiles;
        this.userId = studyMemberById.getUser().getId();
        this.nickname = studyMemberById.getUser().getNickname();
        this.rate = rate;
        this.profileImg = profileImg;
        this.state = studyMemberById.getState();
    }
}
