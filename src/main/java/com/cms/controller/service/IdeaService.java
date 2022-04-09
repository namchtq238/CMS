package com.cms.controller.service;

import com.cms.config.PaginationT;
import com.cms.controller.response.ListIdeaRes;

public interface IdeaService {
    PaginationT<ListIdeaRes> findIdea(Integer page, Integer size);

}
