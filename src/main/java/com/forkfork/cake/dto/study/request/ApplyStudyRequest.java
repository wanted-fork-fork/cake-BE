package com.forkfork.cake.dto.study.request;

import lombok.Data;

import java.util.List;

@Data
public class ApplyStudyRequest {
    Long id;
    List<String> images;
    String content;
}
