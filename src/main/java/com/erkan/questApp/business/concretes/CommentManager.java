package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.CommentService;
import com.erkan.questApp.core.utilities.results.*;
import com.erkan.questApp.entity.Comment;
import com.erkan.questApp.entity.Post;
import com.erkan.questApp.entity.User;
import com.erkan.questApp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Validated
public class CommentManager implements CommentService {

    private final CommentRepository commentRepository;
    private final PostManager postManager;
    private final UserManager userManager;

    public enum Status {
        ACTIVE("A"),
        PASSIVE("P"),
        DELETED("D");

        private final String code;

        Status(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    @Autowired
    public CommentManager(CommentRepository commentRepository, PostManager postManager, UserManager userManager) {
        this.commentRepository = commentRepository;
        this.postManager = postManager;
        this.userManager = userManager;
    }

    @Override
    public DataResult<Comment> getCommentById(@NotNull Long id) {
        Optional<Comment> commentOptional = commentRepository.findByIdAndStatusNot(id, Status.DELETED.getCode());
        return commentOptional
                .map(comment -> new SuccessDataResult<>(comment, "Yorum başarıyla getirildi"))
                .orElseGet(() -> new ErrorDataResult<>("Yorum bulunamadı"));
    }

    @Override
    @Transactional
    public Result createComment(@Valid Comment comment) {
        try {
            DataResult<User> userResult = userManager.getUserById(comment.getUser().getId());
            DataResult<Post> postResult = postManager.getPostById(comment.getPost().getId());

            if (!userResult.isSuccess() || !postResult.isSuccess()) {
                return new ErrorResult("Kullanıcı veya gönderi bulunamadı");
            }

            comment.setUser(userResult.getData());
            comment.setPost(postResult.getData());
            comment.setStatus(Status.ACTIVE.getCode());
            Comment savedComment = commentRepository.save(comment);
            return new SuccessDataResult<>(savedComment, "Yorum başarıyla oluşturuldu");
        } catch (Exception e) {
            return new ErrorResult("Yorum oluşturulurken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result updateComment(@Valid Comment comment) {
        Optional<Comment> existingCommentOptional = commentRepository.findByIdAndStatusNot(comment.getId(), Status.DELETED.getCode());
        if (existingCommentOptional.isPresent()) {
            Comment existingComment = existingCommentOptional.get();
            existingComment.setText(comment.getText());
            Comment updatedComment = commentRepository.save(existingComment);
            return new SuccessDataResult<>(updatedComment, "Yorum başarıyla güncellendi");
        } else {
            return new ErrorResult("Güncellenecek yorum bulunamadı");
        }
    }

    @Override
    public DataResult<Page<Comment>> getAllComments(Pageable pageable) {
        try {
            Page<Comment> comments = commentRepository.findAllByStatusNot(Status.DELETED.getCode(), pageable);
            return new SuccessDataResult<>(comments, "Tüm yorumlar başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<>("Yorumlar getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public DataResult<Page<Comment>> getCommentsByPostId(Long postId, Pageable pageable) {
        try {
            Page<Comment> comments = commentRepository.findByPostIdAndStatusNot(postId, Status.DELETED.getCode(), pageable);
            return new SuccessDataResult<>(comments, "Gönderinin yorumları başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<>("Yorumlar getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public DataResult<Page<Comment>> getCommentsByUserId(Long userId, Pageable pageable) {
        try {
            Page<Comment> comments = commentRepository.findByUserIdAndStatusNot(userId, Status.DELETED.getCode(), pageable);
            return new SuccessDataResult<>(comments, "Kullanıcının yorumları başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<>("Yorumlar getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result softDeleteCommentById(@NotNull Long id) {
        Optional<Comment> commentOptional = commentRepository.findByIdAndStatusNot(id, Status.DELETED.getCode());
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setStatus(Status.DELETED.getCode());
            Comment deletedComment = commentRepository.save(comment);
            return new SuccessDataResult<>(deletedComment, "Yorum başarıyla silindi (soft delete)");
        } else {
            return new ErrorResult("Silinecek yorum bulunamadı");
        }
    }

    @Override
    @Transactional
    public Result changeCommentStatus(@NotNull Long id, @NotNull Status newStatus) {
        Optional<Comment> commentOptional = commentRepository.findByIdAndStatusNot(id, Status.DELETED.getCode());
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setStatus(newStatus.getCode());
            Comment updatedComment = commentRepository.save(comment);
            return new SuccessDataResult<>(updatedComment, "Yorum durumu başarıyla güncellendi");
        } else {
            return new ErrorResult("Durum değiştirilecek yorum bulunamadı");
        }
    }
}