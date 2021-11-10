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

    // kakaoMap location id
    private String location;

    private Long peopleCnt;

    private boolean earlyClosing;

    private String chatRoom;

    private String roomPwd;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;

    //    작성자
    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyCategory> studyCategoryList = new LinkedList<>();

    public void addStudyCategory(StudyCategory studyCategory) {
        if (studyCategoryList == null) {
            studyCategoryList = new LinkedList<>();
        }
        studyCategoryList.add(studyCategory);
        studyCategory.setStudy(this);
    }

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyFile> studyFileList = new LinkedList<>();

    public void addStudyFile(StudyFile studyFile) {
        if (studyFileList == null) {
            studyFileList = new LinkedList<>();
        }
        studyFileList.add(studyFile);
        studyFile.setStudy(this);

    }

    @OneToOne(mappedBy = "study", cascade = CascadeType.ALL)
    private StudyMember studyMember;
}
