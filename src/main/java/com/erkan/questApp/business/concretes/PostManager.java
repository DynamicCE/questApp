package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.PostService;
import com.erkan.questApp.core.*;
import com.erkan.questApp.entity.Post;
import com.erkan.questApp.entity.User;
import com.erkan.questApp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostManager implements PostService {
    private final PostRepository postRepository;
    private final UserManager userManager;

    @Autowired
    public PostManager(PostRepository postRepository, UserManager userManager) {
        this.postRepository = postRepository;
        this.userManager = userManager;
    }

    @Override
    public DataResult<Post> getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gönderi bulunamadı"));
        return new SuccessDataResult<>(post, "Gönderi başarıyla getirildi");
    }

    @Override
    public DataResult<Post> createPost(Post post) {
        User user = userManager.getUserById(post.getUser().getId()).getData();
        post.setUser(user);
        try {
            Post createdPost = postRepository.save(post);
            return new SuccessDataResult<>(createdPost, "Gönderi başarıyla oluşturuldu");
        } catch (Exception e) {
            return new ErrorDataResult<>("Gönderi oluşturulurken bir hata meydana geldi: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public DataResult<Post> updatePost(Post post) {
        Post existingPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new IllegalArgumentException("Gönderi bulunamadı"));
        existingPost.setTitle(post.getTitle());
        existingPost.setText(post.getText());
        try {
            Post updatedPost = postRepository.save(existingPost);
            return new SuccessDataResult<>(updatedPost, "Gönderi başarıyla güncellendi");
        } catch (Exception e) {
            return new ErrorDataResult<>(existingPost, "Gönderi güncellenirken bir hata meydana geldi: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Post>> getAllPosts() {
        try {
            List<Post> posts = postRepository.findAll();
            return new SuccessDataResult<>(posts, "Tüm gönderiler başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<>("Gönderiler getirilirken bir hata meydana geldi: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Post>> getPostsByUserId(Long userId) {
        try {
            List<Post> posts = postRepository.findByUserId(userId);
            return new SuccessDataResult<>(posts, "Kullanıcının gönderileri başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<>("Gönderiler getirilirken bir hata meydana geldi: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public DataResult<Post> deletePostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gönderi bulunamadı"));
        try {
            postRepository.deleteById(id);
            return new SuccessDataResult<>(post, "Gönderi başarıyla silindi");
        } catch (Exception e) {
            return new ErrorDataResult<>(post, "Gönderi silinirken bir hata meydana geldi: " + e.getMessage());
        }
    }
}