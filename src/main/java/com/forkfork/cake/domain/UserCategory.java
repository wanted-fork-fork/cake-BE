package com.forkfork.cake.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 유저의 기브 엔 테이크 관리

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    1. give 2. take
    private int type;

    @ManyToOne
    @JoinColumn
    Category category;

    @ManyToOne
    @JoinColumn
    User user;

}
