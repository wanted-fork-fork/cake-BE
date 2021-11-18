package com.forkfork.cake.dto.paging.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilteringResponse {
    String give;
    String take;
    int type;
}
