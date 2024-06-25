package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.LikeService;
import com.erkan.questApp.core.utilities.results.*;
import com.erkan.questApp.entity.Like;
import com.erkan.questApp.repository.LikeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeManager implements LikeService {
    private static final Logger logger = LoggerFactory.getLogger(LikeManager.class);
    private final LikeRepository likeRepository;

    public LikeManager(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public DataResult<Like> getLikeById(Long id) {
        Optional<Like> likeOptional = likeRepository.findById(id);
        if (likeOptional.isPresent()) {
            return new SuccessDataResult<>(likeOptional.get(), "Beğeni başarıyla getirildi.");
        } else {
            logger.warn("Beğeni bulunamadı.");
            return new ErrorDataResult<>("Beğeni bulunamadı.");
        }
    }

    @Override
    @Transactional
    public Result createLike(Like like) {
        try {
            like.setStatus("A");
            likeRepository.save(like);
            return new SuccessResult("Beğeni başarıyla oluşturuldu.");
        } catch (Exception e) {
            logger.error("Beğeni oluşturulurken bir hata oluştu: " + e.getMessage());
            return new ErrorResult("Beğeni oluşturulurken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result updateLike(Long likeId, Like newLike) {
        if (!likeRepository.existsById(likeId)) {
            return new ErrorResult("Güncellenecek beğeni bulunamadı.");
        }
        Optional<Like> likeOptional = likeRepository.findById(likeId);
        if(!likeOptional.isPresent()) {
            return new ErrorResult("Güncellenecek beğeni bulunamadı.");
        }
        try {
            Like existingLike = likeOptional.get();
            existingLike.setStatus("U");
            likeRepository.save(existingLike);
            return new SuccessResult("Beğeni başarıyla güncellendi.");
        } catch (Exception e) {
            logger.error("Beğeni güncellenirken bir hata oluştu: " + e.getMessage());
            return new ErrorResult("Beğeni güncellenirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public DataResult<Page<Like>> getAllLikes(Pageable pageable) {
        try {
            Page<Like> likes = likeRepository.findAll(pageable);
            return new SuccessDataResult<>(likes, "Beğeniler başarıyla getirildi.");
        } catch (Exception e) {
            logger.error("Beğeniler getirilirken bir hata oluştu: " + e.getMessage());
            return new ErrorDataResult<>("Beğeniler getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public DataResult<Page<Like>> getLikesByPostId(Long postId, Pageable pageable) {
        try {
            Page<Like> likes = likeRepository.findByPostId(postId, pageable);
            return new SuccessDataResult<>(likes, "Gönderiye ait beğeniler başarıyla getirildi.");
        } catch (Exception e) {
            logger.error("Gönderiye ait beğeniler getirilirken bir hata oluştu: " + e.getMessage());
            return new ErrorDataResult<>("Gönderiye ait beğeniler getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public DataResult<Page<Like>> getLikesByUserId(Long userId, Pageable pageable) {
        try {
            Page<Like> likes = likeRepository.findByUserId(userId, pageable);
            return new SuccessDataResult<>(likes, "Kullanıcıya ait beğeniler başarıyla getirildi.");
        } catch (Exception e) {
            logger.error("Kullanıcıya ait beğeniler getirilirken bir hata oluştu: " + e.getMessage());
            return new ErrorDataResult<>("Kullanıcıya ait beğeniler getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result softDeleteLikeById(Long id) {
        Optional<Like> likeOptional = likeRepository.findById(id);
        if (likeOptional.isPresent()) {
            Like like = likeOptional.get();
            like.setStatus("D");
            likeRepository.save(like);
            return new SuccessResult("Beğeni başarıyla silindi");
        }
        return new ErrorResult("Silinecek beğeni bulunamadı.");
    }

    @Override
    public DataResult<Page<Like>> getLikesByCommentId(Long commentId, Pageable pageable) {
        try {
            Page<Like> likes = likeRepository.findByCommentId(commentId, pageable);
            return new SuccessDataResult<>(likes, "Yoruma ait beğeniler başarıyla getirildi.");
        } catch (Exception e) {
            logger.error("Yoruma ait beğeniler getirilirken bir hata oluştu: " + e.getMessage());
            return new ErrorDataResult<>("Yoruma ait beğeniler getirilirken bir hata oluştu: " + e.getMessage());
        }
    }
}