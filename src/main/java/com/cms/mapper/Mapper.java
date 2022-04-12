package com.cms.mapper;

import com.cms.config.dto.UploadFileResDTO;
import com.cms.controller.response.ListIdeaRes;
import com.cms.entity.Idea;

public interface Mapper {
    ListIdeaRes ideaToRes(Idea idea);
}
