package com.forkfork.cake.controller.file;

import com.forkfork.cake.dto.file.response.FileUploadResponse;
import com.forkfork.cake.service.S3Service;
import com.forkfork.cake.util.ResFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<Object> fileUpload(MultipartFile file) throws IOException {

        String path = "profile/";

        String filePath = s3Service.upload(path, file);
        String fileUrl = s3Service.getFileUrl(filePath);

        FileUploadResponse fileUploadResponse = new FileUploadResponse(filePath, fileUrl);

        return ResFormat.response(true, 201, fileUploadResponse);
    }

}
