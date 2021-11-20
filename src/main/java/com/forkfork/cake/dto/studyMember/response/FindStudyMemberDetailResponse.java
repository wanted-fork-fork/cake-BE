package com.forkfork.cake.dto.studyMember.response;

import com.forkfork.cake.domain.StudyMember;
import lombok.Data;

import java.util.List;

@Data
public class FindStudyMemberDetailResponse {
    Long studyMemberId;
    String msg;
    List<String> applyFiles;

    public FindStudyMemberDetailResponse(StudyMember studyMemberById, List<String> applyFiles) {
        this.studyMemberId = studyMemberById.getId();
        this.msg = studyMemberById.getMsg();
        this.applyFiles = applyFiles;
    }
}
