package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.CommentService;
import com.erkan.questApp.core.DataResult;
import com.erkan.questApp.core.ErrorDataResult;
import com.erkan.questApp.core.SuccessDataResult;
import com.erkan.questApp.entity.Comment;
import com.erkan.questApp.entity.Post;
import com.erkan.questApp.entity.User;
import com.erkan.questApp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentManager implements CommentService {
    private final CommentRepository commentRepository;
    private final PostManager postManager;
    private final UserManager userManager;

    @Autowired
    public
    CommentManager ( CommentRepository commentRepository, PostManager postManager, UserManager userManager ) {
        this.commentRepository = commentRepository;
        this.postManager = postManager;
        this.userManager = userManager;
    }

    @Override
    public
    DataResult<Comment> getCommentById ( Long id ) {
        Comment comment = commentRepository.findById ( id )
                .orElseThrow ( () -> new IllegalArgumentException ( "Yorum bulunamadı" ) );
        return new SuccessDataResult<> ( comment, "Yorum başarıyla getirildi" );
    }

    @Override
    public
    DataResult<Comment> createComment ( Comment comment ) {
        Optional<User> user = userManager.getUserById ( comment.getUser ( ).getId ( ) ).getData ( );
        Post post = postManager.getPostById ( comment.getPost ( ).getId ( ) ).getData ( );
        comment.setUser ( user.get () );
        comment.setPost ( post );
        try {
            Comment createdComment = commentRepository.save ( comment );
            return new SuccessDataResult<> ( createdComment, "Yorum başarıyla oluşturuldu" );
        } catch (Exception e) {
            return new ErrorDataResult<> ( "Yorum oluşturulurken bir hata meydana geldi: " + e.getMessage ( ) );
        }
    }

    @Override
    @Transactional
    public
    DataResult<Comment> updateComment ( Comment comment ) {
        Comment existingComment = commentRepository.findById ( comment.getId ( ) )
                .orElseThrow ( () -> new IllegalArgumentException ( "Yorum bulunamadı" ) );
        existingComment.setText ( comment.getText ( ) );
        try {
            Comment updatedComment = commentRepository.save ( existingComment );
            return new SuccessDataResult<> ( updatedComment, "Yorum başarıyla güncellendi" );
        } catch (Exception e) {
            return new ErrorDataResult<> ( existingComment, "Yorum güncellenirken bir hata meydana geldi: " + e.getMessage ( ) );
        }
    }

    @Override
    public
    DataResult<List<Comment>> getAllComments () {
        try {
            List<Comment> comments = commentRepository.findAll ( );
            return new SuccessDataResult<> ( comments, "Tüm yorumlar başarıyla getirildi" );
        } catch (Exception e) {
            return new ErrorDataResult<> ( "Yorumlar getirilirken bir hata meydana geldi: " + e.getMessage ( ) );
        }
    }

    @Override
    public
    DataResult<List<Comment>> getCommentsByPostId ( Long postId ) {
        try {
            List<Comment> comments = commentRepository.findByPostId ( postId );
            return new SuccessDataResult<> ( comments, "Gönderinin yorumları başarıyla getirildi" );
        } catch (Exception e) {
            return new ErrorDataResult<> ( "Yorumlar getirilirken bir hata meydana geldi: " + e.getMessage ( ) );
        }
    }

    @Override
    public
    DataResult<List<Comment>> getCommentsByUserId ( Long userId ) {
        try {
            List<Comment> comments = commentRepository.findByUserId ( userId );
            return new SuccessDataResult<> ( comments, "Kullanıcının yorumları başarıyla getirildi" );
        } catch (Exception e) {
            return new ErrorDataResult<> ( "Yorumlar getirilirken bir hata meydana geldi: " + e.getMessage ( ) );
        }
    }

    @Override
    @Transactional
    public
    DataResult<Comment> deleteCommentById ( Long id ) {
        Comment comment = commentRepository.findById ( id )
                .orElseThrow ( () -> new IllegalArgumentException ( "Yorum bulunamadı" ) );
        try {
            comment.setStatus ( "D" ); // Soft delete: yorumu silinmiş olarak işaretle
            Comment deletedComment = commentRepository.save ( comment );
            return new SuccessDataResult<> ( deletedComment, "Yorum başarıyla silindi" );
        } catch (Exception e) {
            return new ErrorDataResult<> ( comment, "Yorum silinirken bir hata meydana geldi: " + e.getMessage ( ) );
        }
    }
}
