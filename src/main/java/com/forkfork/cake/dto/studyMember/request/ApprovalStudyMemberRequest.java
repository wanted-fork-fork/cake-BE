package com.forkfork.cake.dto.studyMember.request;

import lombok.Data;

@Data
public class ApprovalStudyMemberRequest {
    Long studyMemberId;
    int state;
}
