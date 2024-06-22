package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.LikeService;
import com.erkan.questApp.core.utilities.results.DataResult;
import com.erkan.questApp.core.utilities.results.ErrorDataResult;
import com.erkan.questApp.core.utilities.results.SuccessDataResult;
import com.erkan.questApp.entity.Like;
import com.erkan.questApp.entity.Post;
import com.erkan.questApp.entity.User;
import com.erkan.questApp.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeManager implements LikeService {
    private final LikeRepository likeRepository;
    private final PostManager postManager;
    private final UserManager userManager;

    @Autowired
    public LikeManager(LikeRepository likeRepository, PostManager postManager, UserManager userManager) {
        this.likeRepository = likeRepository;
        this.postManager = postManager;
        this.userManager = userManager;
    }

    @Override
    public DataResult<Like> getLikeById(Long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Beğeni bulunamadı"));
        return new SuccessDataResult<>(like, "Beğeni başarıyla getirildi");
    }

    @Override
    public DataResult<Like> createLike(Like like) {
        User user = userManager.getUserById(like.getUser().getId()).getData();
        Post post = postManager.getPostById(like.getPost().getId()).getData();
        like.setUser(user);
        like.setPost(post);
        try {
            Like createdLike = likeRepository.save(like);
            return new SuccessDataResult<>(createdLike, "Beğeni başarıyla oluşturuldu");
        } catch (Exception e) {
            return new ErrorDataResult<>("Beğeni oluşturulurken bir hata meydana geldi: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Like>> getAllLikes() {
        try {
            List<Like> likes = likeRepository.findAll();
            return new SuccessDataResult<>(likes, "Tüm beğeniler başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<>("Beğeniler getirilirken bir hata meydana geldi: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Like>> getLikesByPostId(Long postId) {
        try {
            List<Like> likes = likeRepository.findByPostId(postId);
            return new SuccessDataResult<>(likes, "Gönderinin beğenileri başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<>("Beğeniler getirilirken bir hata meydana geldi: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Like>> getLikesByUserId(Long userId) {
        try {
            List<Like> likes = likeRepository.findByUserId(userId);
            return new SuccessDataResult<>(likes, "Kullanıcının beğenileri başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<>("Beğeniler getirilirken bir hata meydana geldi: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public DataResult<Like> deleteLikeById(Long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Beğeni bulunamadı"));
        try {
            likeRepository.deleteById(id);
            return new SuccessDataResult<>(like, "Beğeni başarıyla silindi");
        } catch (Exception e) {
            return new ErrorDataResult<>(like, "Beğeni silinirken bir hata meydana geldi: " + e.getMessage());
        }
    }
}