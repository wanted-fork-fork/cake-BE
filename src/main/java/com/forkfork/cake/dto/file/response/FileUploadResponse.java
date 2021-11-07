package com.forkfork.cake.dto.file.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadResponse {
    String path;
    String url;
}
