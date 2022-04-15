package com.cms.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticResponse {
    Integer totalIdeas;
    Integer totalLikes;
    Integer totalComment;
    Integer totalDepartment;
    Integer highestLike;
    public StatisticResponse(Integer totalIdeas,
            Integer totalLikes,
            Integer totalComment,
            Integer totalDepartment,
            Integer highestLike){
        this.totalIdeas = totalIdeas;
        this.totalLikes = totalLikes;
        this.totalComment = totalComment;
        this.totalDepartment = totalDepartment;
        this.highestLike = highestLike;
    }
}
