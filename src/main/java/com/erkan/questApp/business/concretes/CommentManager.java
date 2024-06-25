package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.CommentService;
import com.erkan.questApp.core.utilities.results.*;
import com.erkan.questApp.entity.Comment;
import com.erkan.questApp.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentManager implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentManager.class);
    private final CommentRepository commentRepository;

    public CommentManager(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DataResult<Comment> getCommentById(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            return new SuccessDataResult<>(commentOptional.get(), "Yorum başarıyla getirildi.");
        } else {
            logger.warn ( "Yorum bulunamadı." );
            return new ErrorDataResult<>("Yorum bulunamadı.");
        }
    }

    @Override
    @Transactional
    public Result createComment(Comment comment) {
        try {
            comment.setStatus ( "A" );
            commentRepository.save(comment);
            return new SuccessResult("Yorum başarıyla oluşturuldu.");
        } catch (Exception e) {
            logger.error ( "Yorum oluşturulurken bir hata oluştu: " + e.getMessage() );
            return new ErrorResult("Yorum oluşturulurken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result updateComment(Long commentId, Comment newComment) {
        if (!commentRepository.existsById(commentId)) {
            return new ErrorResult("Güncellenecek yorum bulunamadı.");
        }
        Optional<Comment> commentOptional = commentRepository.findById ( commentId );
        if(!commentOptional.isPresent ()){
            return new ErrorResult("Güncellenecek yorum bulunamadı.");
        }
        try {
            Comment existingComment = commentOptional.get ();
            existingComment.setText ( newComment.getText () );
            existingComment.setStatus ( "U" );
            commentRepository.save(existingComment);
            return new SuccessResult("Yorum başarıyla güncellendi.");
        } catch (Exception e) {
            logger.error ( "Yorum güncellenirken bir hata oluştu: " + e.getMessage() );
            return new ErrorResult("Yorum güncellenirken bir hata oluştu: " + e.getMessage());
        }
    }


    @Override
    @Transactional
    public DataResult<Page<Comment>> getAllComments(Pageable pageable) {
        try {
            Page<Comment> comments = commentRepository.findAll(pageable);
            return new SuccessDataResult<>(comments, "Yorumlar başarıyla getirildi.");
        } catch (Exception e) {
            logger.error ( "Yorumlar getirilirken bir hata oluştu: " + e.getMessage() );
            return new ErrorDataResult<>("Yorumlar getirilirken bir hata oluştu: " + e.getMessage());
        }
    }


    @Override
    public DataResult<Page<Comment>> getCommentsByPostId(Long postId, Pageable pageable) {
        try {
            Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
            return new SuccessDataResult<>(comments, "Gönderiye ait yorumlar başarıyla getirildi.");
        } catch (Exception e) {
            logger.error ( "Gönderiye ait yorumlar getirilirken bir hata oluştu: " + e.getMessage() );
            return new ErrorDataResult<>("Gönderiye ait yorumlar getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public DataResult<Page<Comment>> getCommentsByUserId(Long userId, Pageable pageable) {
        try {
            Page<Comment> comments = commentRepository.findByUserId(userId, pageable);
            return new SuccessDataResult<>(comments, "Kullanıcıya ait yorumlar başarıyla getirildi.");
        } catch (Exception e) {
            logger.error ( "Kullanıcıya ait yorumlar getirilirken bir hata oluştu: " + e.getMessage() );
            return new ErrorDataResult<>("Kullanıcıya ait yorumlar getirilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result softDeleteCommentById(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
            if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setStatus ("D");
            commentRepository.save(comment);
            return new SuccessResult("Yorum başarıyla silindi");
        }
        return new ErrorResult("Silinecek yorum bulunamadı.");
    }

    // yorum durumlarını yönetmek
    /*
    @Override
    @Transactional
    public Result changeCommentStatus(Long id, Comment.Status newStatus) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setStatus(newStatus);
            commentRepository.save(comment);
            return new SuccessResult("Yorum durumu başarıyla güncellendi.");
        }
        return new ErrorResult("Durumu güncellenecek yorum bulunamadı.");
    }
    */
}