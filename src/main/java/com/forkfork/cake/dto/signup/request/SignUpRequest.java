package com.forkfork.cake.dto.signup.request;

import com.forkfork.cake.domain.University;
import com.forkfork.cake.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class SignUpRequest {
    //    1. 이메일 2. 비밀번호 3. 닉네임 4. 프로필 이미지 5. 단과대 6. 학교
    String email;
    String pwd;
    String nickname;
    String img;
    int univCategory;
    Long univ;
    List<Long> give;
    List<Long> take;

    public User toUserEntity(String encodedPwd, University university) {
        if (this.img == null) {
            img = "default/default-profile.png";
        }

        return User.builder()
                .email(this.email)
                .pwd(encodedPwd)
                .nickname(this.nickname)
                .img(this.img)
                .univCategory(this.univCategory)
                .university(university)
                .point(0L)
                .portfolio(null)
                .build();

    }
}
