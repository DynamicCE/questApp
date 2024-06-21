package com.erkan.questApp.business.concretes;

import com.erkan.questApp.business.abstracts.UserService;
import com.erkan.questApp.core.DataResult;
import com.erkan.questApp.core.SuccessDataResult;
import com.erkan.questApp.entity.User;
import com.erkan.questApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return new SuccessDataResult (  userRepository.findById ( id ),"işlem başarılı" );
    }

    @Override
    public
    DataResult<User> createUser ( User user ) {
        return new SuccessDataResult (  userRepository.save ( user ),"işlem başarılı" );
    }

    @Override
    public
    DataResult<User> updateUser ( User user ) {
        return new SuccessDataResult ( userRepository.save ( user ),"kullanıcı güncellendi" );
    }

    @Override
    public
    DataResult<User> getAllUsers () {
        return new SuccessDataResult (  userRepository.findAll (),"tüm kullanıcılar getirildi" );
    }
}
