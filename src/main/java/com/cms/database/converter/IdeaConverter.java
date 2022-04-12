package com.cms.database.converter;

import com.cms.entity.Category;
import com.cms.entity.Comment;
import com.cms.entity.Likes;
import com.cms.entity.Staff;

import java.util.List;

public interface IdeaConverter {
    Long getId();

    String getTimeUp();

    String getDescription();

    Long getStaffId();

     Long getCategory();

    Integer getTotalLike();

    Integer getTotalComment();
}
