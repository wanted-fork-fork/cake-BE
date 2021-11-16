package com.forkfork.cake.dto.study.response;

import com.forkfork.cake.domain.Study;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindStudyDetailResponse {
    Long id;
    List<String> images;
    UserInformationDto user;
    String title;
    Date createdAt;
    Long peopleCnt;
    Date startDate;
    Date endDate;
    List<String> give;
    List<String> take;
    String storeName;
    String storeAddress;
    String content;
    Boolean apply;


    public FindStudyDetailResponse(Study studyById, UserInformationDto userInformation, List<String> give, List<String> take, List<String> images, Boolean apply) {
        this.id = studyById.getId();
        this.images = images;
        this.user = userInformation;
        this.title = studyById.getTitle();
        this.createdAt = studyById.getCreatedAt();
        this.startDate = studyById.getStartDate();
        this.endDate = studyById.getEndDate();
        this.give = give;
        this.take = take;
        this.storeName = studyById.getStoreName();
        this.storeAddress = studyById.getStoreAddress();
        this.content = studyById.getContent();
        this.peopleCnt = studyById.getPeopleCnt();
        this.apply = apply;
    }
}
