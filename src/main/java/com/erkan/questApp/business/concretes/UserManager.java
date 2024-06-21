package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.UserService;
import com.erkan.questApp.core.*;
import com.erkan.questApp.entity.User;
import com.erkan.questApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public
class UserManager implements UserService {
    private
    UserRepository userRepository;

    @Autowired
    public
    UserManager ( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }


    @Override
    public
    DataResult<Optional<User>> getUserById ( Long id ) {
        return new SuccessDataResult ( userRepository.findById ( id ), "işlem başarılı" );
    }

    @Override
    public
    DataResult<User> createUser ( User user ) {
        return new SuccessDataResult ( userRepository.save ( user ), "işlem başarılı" );
    }

    @Override
    public
    DataResult<User> updateUserPassword ( Long id, String password ) {
        Optional<User> oldUser = userRepository.findById ( id );
        if (oldUser.isPresent ( )) {
            User updatable = oldUser.get ( );
            updatable.setPassword ( password );
            return new SuccessDataResult<> ( userRepository.save ( updatable ), "Şifreniz değiştirildi" );
        }else {
            return new ErrorDataResult<>("Kullanıcı bulunamadı");
        }

    }

    @Override
    public
    DataResult<User> getAllUsers () {
        return new SuccessDataResult ( userRepository.findAll ( ), "tüm kullanıcılar getirildi" );
    }


    @Override
    @Transactional
    public
    Result deleteById ( Long id ) {
        if (!userRepository.existsById ( id )) {
            return new ErrorResult ( "Silinecek kullanıcı bulunamadı" );
        }
        try {
            userRepository.deleteById ( id );
            return new SuccessResult ( "Kullanıcı başarıyla silindi" );
        } catch (Exception e) {
            return new ErrorResult ( "Kullanıcı silinirken bir hata meydana geldi" );
        }
    }


}
