package com.forkfork.cake.service;

import com.forkfork.cake.domain.Category;
import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.StudyCategory;
import com.forkfork.cake.dto.category.SeperateCategoryDto;
import com.forkfork.cake.repository.StudyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudyCategoryService {
    private final StudyCategoryRepository studyCategoryRepository;

    public List<StudyCategory> findStudyCategoryByStudy(Study study) {
        return studyCategoryRepository.findByStudy(study);
    }

    public List<StudyCategory> findStudyCategoryByCategory(Category giveCategory) {
        return studyCategoryRepository.findByCategory(giveCategory);
    }

    public Page<StudyCategory> findStudyByfiltering(Long give, Long take, Pageable pageRequest) {
        return studyCategoryRepository.findStudyByfiltering(give, take, pageRequest);
    }

    public SeperateCategoryDto seperateCategory(List<StudyCategory> existStudyCategory) {
        List<String> give = new LinkedList<>();
        List<String> take = new LinkedList<>();
        for (StudyCategory studyCategory:
                existStudyCategory) {
            Category category = studyCategory.getCategory();
            String categoryName = category.getName();

            if (categoryName.equals("현금")) {
                categoryName = studyCategory.getStudy().getPoint() +"원";
            }

            if (studyCategory.getType() == 1) {
                give.add(categoryName);
            } else {
                take.add(categoryName);
            }
        }

        return new SeperateCategoryDto(give, take);
    }
}
