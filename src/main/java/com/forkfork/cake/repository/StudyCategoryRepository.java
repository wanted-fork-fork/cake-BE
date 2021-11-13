package com.forkfork.cake.repository;

import com.forkfork.cake.domain.Category;
import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface StudyCategoryRepository extends JpaRepository<StudyCategory, Long> {
    List<StudyCategory> findByStudy(Study study);

    List<StudyCategory> findByCategory(Category giveCategory);

    @Query(nativeQuery = true, value = "select * from study_category where study_id in (select study_id from study_category where category_id = :give and type = 1) and type = 2 and category_id = :take")
    Page<StudyCategory> findStudyByfiltering(@Param(value = "give") Long give, @Param(value = "take") Long take, Pageable pageRequest);
}
