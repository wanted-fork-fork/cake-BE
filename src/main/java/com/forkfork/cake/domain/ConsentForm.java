package com.forkfork.cake.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 이용약관 동의 폼
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ConsentForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    이용약관
    private boolean serviceRule;
//    개인정보처리방침
    private boolean personalInfo;

    @OneToOne
    @JoinColumn
    private User user;
}
