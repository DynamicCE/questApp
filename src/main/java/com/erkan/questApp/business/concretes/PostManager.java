package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.PostService;
import com.erkan.questApp.core.utilities.results.*;
import com.erkan.questApp.entity.Post;
import com.erkan.questApp.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostManager implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostManager.class);
    private final PostRepository postRepository;

    public PostManager(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public DataResult<Post> getPostById(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            return new SuccessDataResult<>(postOptional.get(), "Gönderi başarıyla getirildi.");
        } else {
            logger.warn("Gönderi bulunamadı.");
            return new ErrorDataResult<>("Gönderi bulunamadı.");
        }
    }

    @Override
    @Transactional
    public Result createPost(Post post) {
        try {
            post.setStatus("A");
            postRepository.save(post);
            return new SuccessResult("Gönderi başarıyla oluşturuldu.");
        } catch (Exception e) {
            logger.error("Gönderi oluşturulurken bir hata oluştu: " + e.getMessage());
            return new ErrorResult("Gönderi oluşturulurken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result updatePost(Long postId, Post newPost) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if(!postOptional.isPresent()) {
            return new ErrorResult("Güncellenecek gönderi bulunamadı.");
        }
        try {
            Post existingPost = postOptional.get();
            existingPost.setTitle(newPost.getTitle());
            existingPost.setStatus("U");
            postRepository.save(existingPost);
            return new SuccessResult("Gönderi başarıyla güncellendi.");
        } catch (Exception e) {
            logger.error("Gönderi güncellenirken bir hata oluştu: " + e.getMessage());
            return new ErrorResult("Gönderi güncellenirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public DataResult<Page<Post>> getAllPosts(Pageable pageable) {
        try {
            Page<Post> posts = postRepository.findAll(pageable);
            return new SuccessDataResult<>(posts, "Gönderiler başarıyla getirildi.");
        } catch (Exception e) {
            logger.error("Gönderiler getirilirken bir hata oluştu: " + e.getMessage());
            return new ErrorDataResult<>("Gönderiler getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public DataResult<Page<Post>> getPostsByUserId(Long userId, Pageable pageable) {
        try {
            Page<Post> posts = postRepository.findByUserId(userId, pageable);
            return new SuccessDataResult<>(posts, "Kullanıcıya ait gönderiler başarıyla getirildi.");
        } catch (Exception e) {
            logger.error("Kullanıcıya ait gönderiler getirilirken bir hata oluştu: " + e.getMessage());
            return new ErrorDataResult<>("Kullanıcıya ait gönderiler getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result softDeletePostById(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setStatus("D");
            postRepository.save(post);
            return new SuccessResult("Gönderi başarıyla silindi");
        }
        return new ErrorResult("Silinecek gönderi bulunamadı.");
    }

    @Override
    public DataResult<Post> getPostWithLikesAndComments(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            // Hibernate'in lazy loading yapmasını sağlamak için likes ve comments'i başlatıyoruz
            post.getLikes().size();
            post.getComments().size();
            return new SuccessDataResult<>(post, "Gönderi, beğenileri ve yorumları ile birlikte getirildi.");
        } else {
            return new ErrorDataResult<>("Gönderi bulunamadı.");
        }
    }

    @Override
    @Transactional
    public Result updatePostTitle(Long postId, String newTitle) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(newTitle);
            post.setStatus("U");
            postRepository.save(post);
            return new SuccessResult("Gönderi başlığı başarıyla güncellendi.");
        }
        return new ErrorResult("Güncellenecek gönderi bulunamadı.");
    }
}