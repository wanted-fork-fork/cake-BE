package com.forkfork.cake.dto.study.response;

import com.forkfork.cake.domain.User;
import lombok.Data;

@Data
public class UserInformationDto {
    String img;
    String nickname;
    Double rate;
    Long id;

    public UserInformationDto(User user, String fileUrl, Double rate) {
        this.img = fileUrl;
        this.nickname = user.getNickname();
        this.rate = rate;
        this.id = user.getId();
    }
}
