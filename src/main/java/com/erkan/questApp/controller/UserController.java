package com.erkan.questApp.controller;

import com.erkan.questApp.business.abstracts.UserService;
import com.erkan.questApp.core.DataResult;
import com.erkan.questApp.core.ErrorDataResult;
import com.erkan.questApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
public
class UserController {
    private
    UserService userService;

    @Autowired
    public
    UserController ( UserService userService ) {
        this.userService = userService;
    }


    @GetMapping("getAllUsers")
    ResponseEntity<DataResult<User>> getAllUsers () {
        DataResult<User> result = userService.getAllUsers ( );
        return ResponseEntity.ok ( result );
    }


    @GetMapping("/{id}/getUserById")
    ResponseEntity<DataResult<Optional<User>>> getUserById ( @PathVariable Long id ) {
        DataResult<Optional<User>> result = userService.getUserById ( id );
        return ResponseEntity.ok ( result );
    }

    @PostMapping("createUser")
    ResponseEntity<DataResult<User>> createUser ( @RequestBody User user ) {
        DataResult<User> result = userService.createUser ( user );
        return ResponseEntity.ok ( result );
    }

    @PutMapping("/{id}/updateUserPassword")
    public
    ResponseEntity<DataResult<User>> updateUserPassword ( @PathVariable Long id, @RequestBody String newPassword ) {
        DataResult<Optional<User>> oldUser = userService.getUserById ( id );
        if (!oldUser.isSuccess ( ) && oldUser.getData ( ).isEmpty ( )) {
            return ResponseEntity.status ( HttpStatus.NOT_FOUND ).body ( new ErrorDataResult<> ( "Kullanıcı bulunamadı" ) );
        }
        User updatable = oldUser.getData ( ).get ( );
        updatable.setPassword ( newPassword );
        DataResult<User> result = userService.updateUser ( updatable );
        return ResponseEntity.ok ( result);
    }

    @DeleteMapping("{id}/deleteUser")
    ResponseEntity<DataResult<User>> deleteUser(@PathVariable Long id){
        DataResult<Optional<User>> deletable = userService.getUserById ( id );
        userService.deleteById(id);

    }
}
