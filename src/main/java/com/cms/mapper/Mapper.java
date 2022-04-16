package com.cms.mapper;

import com.cms.config.dto.UploadFileResDTO;
import com.cms.controller.response.*;
import com.cms.database.converter.DepartmentConverter;
import com.cms.entity.*;

public interface Mapper {
    ListIdeaRes ideaToRes(Idea idea);
    StaffRes entityStaffToStaffRes(User user);
    CategoryRes entityCategoryToCategoryRes(Category category);
    CommentRes entityCommentToCommentRes(Comment comment);
    DepartmentResponse converterDepartmentToDepartmentRes(DepartmentConverter departments);
    DepartmentResponse entityToDepartmentRes(Departments departments);
}
