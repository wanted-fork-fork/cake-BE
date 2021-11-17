package com.forkfork.cake.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StudyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    1. 스터디 생성자, 2. 신청자, 3. 참여자, 4. 참여 반려자
    private int state;

    // 신청 시 함께 남긴 말
    private String msg;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne
    @JoinColumn
    private Study study;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "studyMember", cascade = CascadeType.ALL)
    List<ApplyFile> applyFileList = new LinkedList<>();

    public void addApplyFile(ApplyFile applyFile) {
        if (applyFileList == null) {
            applyFileList = new LinkedList<>();
        }
        applyFileList.add(applyFile);
        applyFile.setStudyMember(this);
    }
}
