package com.forkfork.cake.dto.category.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAllCategoryReponse {
    Long id;
    String name;
    String img;

}
