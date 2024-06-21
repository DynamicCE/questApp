package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.UserService;
import com.erkan.questApp.core.*;
import com.erkan.questApp.entity.User;
import com.erkan.questApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserManager implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public DataResult<Optional<User>> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent() && !"D".equals(userOptional.get().getStatus())) {
            return new SuccessDataResult(userOptional, "Kullanıcı başarıyla getirildi");
        } else {
            return new ErrorDataResult<>("Kullanıcı bulunamadı veya silinmiş");
        }
    }

    @Override
    public DataResult<User> createUser(User user) {
        user.setStatus("A"); // Yeni kullanıcıyı aktif olarak işaretle
        try {
            User createdUser = userRepository.save(user);
            return new SuccessDataResult<>(createdUser, "Kullanıcı başarıyla oluşturuldu");
        } catch (Exception e) {
            return new ErrorDataResult<>("Kullanıcı oluşturulurken bir hata meydana geldi: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public DataResult<User> updateUserPassword(Long id, String password) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent() && !"D".equals(userOptional.get().getStatus())) {
            User user = userOptional.get();
            user.setPassword(password);
            try {
                User updatedUser = userRepository.save(user);
                return new SuccessDataResult<>(updatedUser, "Kullanıcı şifresi başarıyla güncellendi");
            } catch (Exception e) {
                return new ErrorDataResult<>(user, "Kullanıcı şifresi güncellenirken bir hata meydana geldi: " + e.getMessage());
            }
        } else {
            return new ErrorDataResult<>("Kullanıcı bulunamadı veya silinmiş");
        }
    }

    @Override
    public DataResult<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<User> activeUsers = new ArrayList<>();
            for (User user : users) {
                if (!"D".equals(user.getStatus())) {
                    activeUsers.add(user);
                }
            }
            return new SuccessDataResult<>(activeUsers, "Tüm kullanıcılar başarıyla getirildi");
        } catch (Exception e) {
            return new ErrorDataResult<>("Kullanıcılar getirilirken bir hata meydana geldi: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public DataResult<User> deleteById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus("D");
            try {
                userRepository.save(user);
                return new SuccessDataResult<>(user, "Kullanıcı başarıyla silindi");
            } catch (Exception e) {
                return new ErrorDataResult<>(user, "Kullanıcı silinirken bir hata meydana geldi: " + e.getMessage());
            }
        } else {
            return new ErrorDataResult<>("Silinecek kullanıcı bulunamadı");
        }
    }
}
