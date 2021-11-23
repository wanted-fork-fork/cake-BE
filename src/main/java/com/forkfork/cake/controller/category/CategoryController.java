package com.forkfork.cake.controller.category;

import com.forkfork.cake.domain.Category;
import com.forkfork.cake.dto.category.response.FindAllCategoryReponse;
import com.forkfork.cake.service.CategoryService;
import com.forkfork.cake.service.S3Service;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final S3Service s3Service;
    private final CategoryService categoryService;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadCategory(MultipartFile file, HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");
        String path = "category/";

        String upload = s3Service.upload(path, file);
        String fileUrl = s3Service.getFileUrl(upload);

        Category category = Category.builder().name(name).img(fileUrl).build();

        categoryService.saveCategory(category);

        return ResFormat.response(true, 201, "카테고리 등록을 완료했습니다.");
    }

    @GetMapping
    public ResponseEntity<Object> findCategoryAll(@RequestParam boolean point) {
        List<Category> categoryAll = categoryService.findCategoryAll();
        List<FindAllCategoryReponse> categoryReponseList = new LinkedList<>();

        for (Category category:
             categoryAll) {
            if (!point && category.getName().equals("현금")) {
                continue;
            }
            categoryReponseList.add(new FindAllCategoryReponse(category.getId(), category.getName(), category.getImg()));
        }

        return ResFormat.response(true, 200, categoryReponseList);

    }
}
