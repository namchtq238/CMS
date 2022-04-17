package com.cms.mapper;

import com.cms.controller.response.*;
import com.cms.database.converter.DepartmentConverter;
import com.cms.entity.*;
import org.springframework.stereotype.Service;

@Service
public class MapperImp implements Mapper{

    @Override
    public ListIdeaRes ideaToRes(Idea idea) {
        if(idea == null) return null;
        ListIdeaRes res = new ListIdeaRes();
        res.setName(idea.getName());
        res.setCategoryId(idea.getCategoryId());
        res.setIdeaId(idea.getId());
        res.setDepartmentId(idea.getDepartmentId());
        res.setTimeUp(idea.getTimeUp() == null ? null : idea.getTimeUp().toString());
        res.setDescription(idea.getDescription());
        res.setStaffId(idea.getUserId());
        return res;
    }

    @Override
    public StaffRes entityStaffToStaffRes(User user) {
        if(user == null) return null;
        return StaffRes.builder()
                .staffId(user.getId())
                .address(user.getAddress())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .username(user.getUserName())
                .build();
    }

    @Override
    public CategoryRes entityCategoryToCategoryRes(Category category) {
        if(category == null) return null;
        CategoryRes res = new CategoryRes();
        res.setActive(category.isActive());
        res.setCreatedDate(category.getCreatedDate().toString());
        res.setName(category.getDescription());
        res.setId(category.getId());
        return res;
    }

    @Override
    public CommentRes entityCommentToCommentRes(Comment comment) {
        if(comment == null) return null;
        CommentRes res = new CommentRes();
        if(comment == null) return null;
        res.setContent(comment.getContent());
        res.setAnonymous(comment.isAnonymous());
        res.setStaffId(comment.getUserId());
        return res;
    }

    @Override
    public DepartmentResponse converterDepartmentToDepartmentRes(DepartmentConverter converter) {
        DepartmentResponse res = new DepartmentResponse();
        if(converter == null) return null;
        res.setId(converter.getId());
        res.setName(converter.getName());
        res.setQaName(converter.getQaName());
        res.setStartDate(converter.getStartDate());
        res.setClosureDate(converter.getClosureDate());
        res.setClosureDateIdea(converter.getClosureDateIdea());
        res.setQaId(converter.getQaId());
        return res;
    }

    @Override
    public DepartmentResponse entityToDepartmentRes(Departments departments) {
        if(departments == null) return null;
        DepartmentResponse response = new DepartmentResponse();
        response.setName(departments.getName());
        response.setClosureDate(departments.getClosureDate());
        response.setClosureDateIdea(departments.getClosureDateIdea());
        response.setId(departments.getId());
        response.setStartDate(departments.getStartDate());
        return response;
    }
}
