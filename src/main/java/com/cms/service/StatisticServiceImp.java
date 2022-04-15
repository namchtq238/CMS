package com.cms.service;

import com.cms.controller.response.StatisticResponse;
import com.cms.controller.service.StatisticService;
import com.cms.database.CommentRepo;
import com.cms.database.DepartmentsRepo;
import com.cms.database.IdeaRepository;
import com.cms.database.LikeRepo;
import com.cms.entity.Comment;
import com.cms.entity.Departments;
import com.cms.entity.Idea;
import com.cms.entity.Likes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImp implements StatisticService {
    @Autowired
    LikeRepo likeRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    IdeaRepository ideaRepository;

    @Autowired
    DepartmentsRepo departmentsRepo;

    @Override
    public StatisticResponse getListStatistic() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -4);
        Instant processTime = cal.getTime().toInstant();
        List<Departments> departments = departmentsRepo.findAll();
        int totalDepartments = (int) departments.stream().filter(department ->
                department.getStartDate().isAfter(processTime)).count();
        List<Idea> ideas = ideaRepository.findAll();
        int totalIdeas = (int) ideas.stream().filter(idea -> idea.getCreatedDate().isAfter(processTime)).count();

        List<Likes> likes = likeRepo.findAll();
        int totalLikes = (int) likes.stream().filter(like -> like.getCreatedAt().isAfter(processTime)
                || like.getCreatedAt().isAfter(processTime)).count();

        List<Comment> comments = commentRepo.findAll();
        int totalComments = (int) comments.stream().filter(comment -> comment.getCreatedDate().isAfter(processTime)).count();
        List<Integer> likeListInIdea = new ArrayList<>();
        for(Idea element : ideas){
            Integer listLikeInIdea = likeRepo.countLikesByIdeaId(element.getId());
            likeListInIdea.add(listLikeInIdea);
        }
        Collections.sort(likeListInIdea, Collections.reverseOrder());
        return new StatisticResponse(totalIdeas,totalLikes,totalComments,totalDepartments,likeListInIdea.isEmpty() ? 0 : likeListInIdea.get(0));
    }
}
