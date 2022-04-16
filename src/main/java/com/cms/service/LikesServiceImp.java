package com.cms.service;

import com.cms.constants.LikeStatus;
import com.cms.controller.request.ChangeStatusReq;
import com.cms.controller.service.LikesService;
import com.cms.database.LikeRepo;
import com.cms.database.UserRepository;
import com.cms.entity.Likes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

@Service
public class LikesServiceImp implements LikesService {
    @Autowired
    LikeRepo likeRepo;
    @Autowired
    UserRepository userRepo;

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public void changeStatusLike(ChangeStatusReq req) {
            Optional<Likes> likes = likeRepo.findByStaffIdAndIdeaId(req.getStaffId(), req.getIdeaId());
            if(likes.isPresent()) {
                Likes like = likes.get();
                like.setIsLike(req.getStatus());
                likeRepo.save(like);
                like.setUpdatedAt(Instant.now());
            }
            else {
                Likes like = new Likes();
                like.setIdeaId(req.getIdeaId());
                like.setUserId(req.getStaffId());
                like.setIsLike(req.getStatus());
                like.setCreatedAt(Instant.now());
                likeRepo.save(like);
            }
    }


    @Override
    public int countLike(Long postId) {
        return likeRepo.countLikesByIdeaId(postId);
    }
}
