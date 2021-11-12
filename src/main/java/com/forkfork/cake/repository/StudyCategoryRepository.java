package com.forkfork.cake.repository;

import com.forkfork.cake.domain.Category;
import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyCategoryRepository extends JpaRepository<StudyCategory, Long> {
    List<StudyCategory> findByStudy(Study study);

    List<StudyCategory> findByCategory(Category giveCategory);
}
