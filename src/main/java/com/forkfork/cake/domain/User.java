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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String pwd;

    private String nickname;

    // 1.인문대 2.사회대 3.공대 4.자연대 5.경영대 6.사범대 7.예술대 8.정보통신
    private int univCategory;

    //    유저 프로필
    @Column(columnDefinition = "TEXT")
    private String img;

    @Column(columnDefinition = "TEXT")
    private String portfolio;

    private Long point;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @JoinColumn
    @ManyToOne
    private University university;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCategory> userCategoryList = new LinkedList<>();

    public void addUserCategory(UserCategory category) {
        if (userCategoryList == null) {
            userCategoryList = new LinkedList<>();
        }
        userCategoryList.add(category);
        category.setUser(this);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Study> studyList = new LinkedList<>();

    public void addStudy(Study study) {
        if (studyList == null) {
            studyList = new LinkedList<>();
        }
        studyList.add(study);
        study.setUser(this);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PointRecord> pointRecordList = new LinkedList<>();

    public void addPointRecord(PointRecord pointRecord) {
        if (pointRecordList == null) {
            pointRecordList = new LinkedList<>();
        }
        pointRecordList.add(pointRecord);
        pointRecord.setUser(this);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<StudyMember> studyMemberList = new LinkedList<>();

    public void addStudyMember(StudyMember studyMember) {
        if (studyMemberList == null) {
            studyMemberList = new LinkedList<>();
        }
        studyMemberList.add(studyMember);
        studyMember.setUser(this);
    }

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    private List<Review> fromReviewList = new LinkedList<>();

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
    private List<Review> toReviewList = new LinkedList<>();

    public void addReview(Review review) {
        if (fromReviewList == null) {
            fromReviewList = new LinkedList<>();
        }
        if (toReviewList == null) {
            toReviewList = new LinkedList<>();
        }
        fromReviewList.add(review);
        review.setFromUser(this);

        toReviewList.add(review);
        review.setToUser(this);
    }


    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    private List<PointDeal> fromUserList = new LinkedList<>();

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
    private List<PointDeal> toUserList = new LinkedList<>();

    public void addPointDeal(PointDeal pointDeal) {
        if (fromUserList == null) {
            fromUserList = new LinkedList<>();
        }
        if (toUserList == null) {
            toUserList = new LinkedList<>();
        }
        fromUserList.add(pointDeal);
        pointDeal.setFromUser(this);

        toUserList.add(pointDeal);
        pointDeal.setToUser(this);
    }

    @OneToOne(mappedBy = "user")
    private Auth auth;

    @OneToOne(mappedBy = "user")
    private ConsentForm consentForm;

}
