package com.cms.service;

import com.cms.constants.LikeStatus;
import com.cms.controller.request.ChangeStatusReq;
import com.cms.controller.service.LikesService;
import com.cms.database.LikeRepo;
import com.cms.database.UserRepository;
import com.cms.entity.Idea;
import com.cms.entity.Likes;
import com.cms.entity.Staff;
import lombok.extern.java.Log;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class LikesServiceImp implements LikesService {
    @Autowired
    LikeRepo likeRepo;
    @Autowired
    UserRepository userRepo;

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public Integer changeStatusLike(ChangeStatusReq req) {
        try {
            Optional<Likes> likes = likeRepo.findByStaffIdAndIdeaId(req.getStaffId(), req.getIdeaId());
            if (likes.isEmpty()) {
                likeRepo.saveLikeByStaffIdAndIdeaId(userRepo.findStaffIdByUserId(req.getStaffId()), req.getIdeaId(), Instant.now());
                return 1;
            } else {
                Likes likeObj = likes.get();
                Integer newStatus = req.getStatus();


                if (LikeStatus.INACTIVE.getValue().equals(req.getStatus())) {
                    newStatus = LikeStatus.LIKE.getValue();
                    likeObj.setIsLike(newStatus);
                    likeObj.setUpdatedAt(Instant.now());
                    likeRepo.save(likeObj);
                }

                if (LikeStatus.LIKE.getValue().equals(req.getStatus())) {
                    newStatus = LikeStatus.DISLIKE.getValue();
                    likeObj.setIsLike(newStatus);
                    likeObj.setUpdatedAt(Instant.now());
                    likeRepo.save(likeObj);
                }

                if (LikeStatus.DISLIKE.getValue().equals(req.getStatus())) {
                    newStatus = LikeStatus.INACTIVE.getValue();
                    likeObj.setIsLike(newStatus);
                    likeObj.setUpdatedAt(Instant.now());
                    likeRepo.save(likeObj);
                }
                return newStatus;
            }
        } catch (Exception e) {
            return req.getStatus();
        }
    }


    @Override
    public int countLike(Long postId) {
        return likeRepo.countLikesByIdeaId(postId);
    }
}
