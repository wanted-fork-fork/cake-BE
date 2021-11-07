package com.forkfork.cake.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;
    private String accessToken;

    @OneToOne
    @JoinColumn
    private User user;


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
