package com.forkfork.cake.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SeperateCategoryDto {
    List<String> give;
    List<String> take;
}
