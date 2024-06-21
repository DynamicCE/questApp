package com.erkan.questApp.business.abstracts;

import com.erkan.questApp.core.DataResult;
import com.erkan.questApp.core.Result;
import com.erkan.questApp.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public
interface UserService {

    DataResult<Optional<User>> getUserById ( Long id );

    DataResult<User> createUser ( User user );

    DataResult<User> updateUserPassword ( Long id, String password );

    DataResult<User> getAllUsers ();

    DataResult<User> deleteById(Long id);
}
