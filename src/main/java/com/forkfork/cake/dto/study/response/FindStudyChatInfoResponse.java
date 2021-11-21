package com.forkfork.cake.dto.study.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FindStudyChatInfoResponse {
    String chatRoom;
    String roomPwd;
}
