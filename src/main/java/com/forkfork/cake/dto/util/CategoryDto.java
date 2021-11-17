package com.forkfork.cake.dto.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {
    Long id;
    String name;
    String img;
}
