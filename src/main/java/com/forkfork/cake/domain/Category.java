package com.forkfork.cake.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //    카테고리 이미지
    private String img;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<UserCategory> userCategoryList = new LinkedList<>();

    public void addUserCategory(UserCategory category) {
        userCategoryList.add(category);
        category.setCategory(this);
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<StudyCategory> studyCategoryList = new LinkedList<>();

    public void addStudyCategory(StudyCategory studyCategory) {
        studyCategoryList.add(studyCategory);
        studyCategory.setCategory(this);
    }
}
