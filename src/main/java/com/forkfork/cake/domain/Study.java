package com.forkfork.cake.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    //    1. 1:1, 2. 1:n, 3. n
    private int type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    private String location;

    private Long peopleCnt;

    //    1. 모집중 2. 예약중(임시) 3. 마감
    private int state;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    //    작성자
    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyCategory> studyCategoryList = new LinkedList<>();

    public void addStudyCategory(StudyCategory studyCategory) {
        studyCategoryList.add(studyCategory);
        studyCategory.setStudy(this);
    }

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyFile> studyFileList = new LinkedList<>();

    public void addStudyFile(StudyFile studyFile) {
        studyFileList.add(studyFile);
        studyFile.setStudy(this);
    }

    @OneToOne(mappedBy = "study", cascade = CascadeType.ALL)
    private StudyMember studyMember;
}
