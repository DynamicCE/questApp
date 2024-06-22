package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.CommentService;
import com.erkan.questApp.core.utilities.results.DataResult;
import com.erkan.questApp.core.utilities.results.ErrorDataResult;
import com.erkan.questApp.core.utilities.results.Result;
import com.erkan.questApp.core.utilities.results.SuccessDataResult;
import com.erkan.questApp.core.utilities.results.ErrorResult;
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
        return commentRepository.findByIdAndStatusNot(id, Status.DELETED.getCode())
                .map(comment -> new SuccessDataResult<>(comment, "Yorum başarıyla getirildi"))
                .orElseGet(() -> new ErrorDataResult<Comment>("Yorum bulunamadı"));
    }

    @Override
    @Transactional
    public Result createComment(@Valid Comment comment) {
        try {
            Optional<User> userOptional = Optional.ofNullable(userManager.getUserById(comment.getUser().getId()).getData());
            Optional<Post> postOptional = Optional.ofNullable(postManager.getPostById(comment.getPost().getId()).getData());

            if (userOptional.isEmpty() || postOptional.isEmpty()) {
                return new ErrorResult("Kullanıcı veya gönderi bulunamadı");
            }

            comment.setUser(userOptional.get());
            comment.setPost(postOptional.get());
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
        return commentRepository.findByIdAndStatusNot(comment.getId(), Status.DELETED.getCode())
                .map(existingComment -> {
                    existingComment.setText(comment.getText());
                    Comment updatedComment = commentRepository.save(existingComment);
                    return new SuccessDataResult<>(updatedComment, "Yorum başarıyla güncellendi");
                })
                .orElseGet(() -> new ErrorDataResult<Comment>("Güncellenecek yorum bulunamadı"));
    }

    @Override
    public DataResult<Page<Comment>> getAllComments(Pageable pageable) {
        try {
            Page<Comment> comments = commentRepository.findAllByStatusNot(Status.DELETED.getCode(), pageable);
            return new SuccessDataResult<>(comments, "Tüm yorumlar başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<Page<Comment>>("Yorumlar getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public DataResult<Page<Comment>> getCommentsByPostId(Long postId, Pageable pageable) {
        try {
            Page<Comment> comments = commentRepository.findByPostIdAndStatusNot(postId, Status.DELETED.getCode(), pageable);
            return new SuccessDataResult<>(comments, "Gönderinin yorumları başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<Page<Comment>>("Yorumlar getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public DataResult<Page<Comment>> getCommentsByUserId(Long userId, Pageable pageable) {
        try {
            Page<Comment> comments = commentRepository.findByUserIdAndStatusNot(userId, Status.DELETED.getCode(), pageable);
            return new SuccessDataResult<>(comments, "Kullanıcının yorumları başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<Page<Comment>>("Yorumlar getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result softDeleteCommentById(@NotNull Long id) {
        return commentRepository.findByIdAndStatusNot(id, Status.DELETED.getCode())
                .map(comment -> {
                    comment.setStatus(Status.DELETED.getCode());
                    Comment deletedComment = commentRepository.save(comment);
                    return new SuccessDataResult<>(deletedComment, "Yorum başarıyla silindi (soft delete)");
                })
                .orElseGet(() -> new ErrorDataResult<Comment>("Silinecek yorum bulunamadı"));
    }

    @Override
    @Transactional
    public Result changeCommentStatus(@NotNull Long id, @NotNull Status newStatus) {
        return commentRepository.findByIdAndStatusNot(id, Status.DELETED.getCode())
                .map(comment -> {
                    comment.setStatus(newStatus.getCode());
                    Comment updatedComment = commentRepository.save(comment);
                    return new SuccessDataResult<>(updatedComment, "Yorum durumu başarıyla güncellendi");
                })
                .orElseGet(() -> new ErrorDataResult<Comment>("Durum değiştirilecek yorum bulunamadı"));
    }
}
