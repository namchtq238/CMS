package com.cms.mapper;

import com.cms.controller.response.ListIdeaRes;
import com.cms.entity.Idea;
import org.springframework.stereotype.Service;

@Service
public class MapperImp implements Mapper{

    @Override
    public ListIdeaRes ideaToRes(Idea idea) {
        ListIdeaRes res = new ListIdeaRes();
        res.setName(idea.getName());
        res.setCategoryId(idea.getCategory().getId());
        res.setIdeaId(idea.getId());
        res.setDepartmentId(idea.getDepartmentId());
        res.setTimeUp(idea.getTimeUp() == null ? null : idea.getTimeUp().toString());
        res.setDescription(idea.getDescription());
//        res.setStaffId(idea.getStaff().getId());
        return res;
    }
}
