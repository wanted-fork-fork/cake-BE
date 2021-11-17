package com.forkfork.cake.dto.user.response;

import com.forkfork.cake.domain.User;
import com.forkfork.cake.dto.util.CategoryDto;
import lombok.Data;

import java.util.List;

@Data
public class FindMyPageResponse {
    String profileImg;
    String nickname;
    String email;
    Long point;
    Double rate;
    Long studyCnt;
    List<CategoryDto> give;
    List<CategoryDto> take;

    public FindMyPageResponse(User userByEmail, Double userRate, String profileImg, List<CategoryDto> give, List<CategoryDto> take, Long studyCnt) {
        this.profileImg = profileImg;
        this.nickname = userByEmail.getNickname();
        this.email = userByEmail.getEmail();
        this.point = userByEmail.getPoint();
        this.rate = userRate;
        this.give = give;
        this.take = take;
        this.studyCnt = studyCnt;
    }
}
