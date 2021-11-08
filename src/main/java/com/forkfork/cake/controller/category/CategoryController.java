package com.forkfork.cake.controller.category;

import com.forkfork.cake.domain.Category;
import com.forkfork.cake.service.CategoryService;
import com.forkfork.cake.service.S3Service;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
}
