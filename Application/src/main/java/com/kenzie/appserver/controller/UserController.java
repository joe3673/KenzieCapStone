package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.controller.model.UserUpdateRequest;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity <UserResponse> searchUserById(@PathVariable("userId")String userId){
        User user = userService.findByUserId(userId);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        UserResponse userResponse = createUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addNewUser(@RequestBody UserCreateRequest userCreateRequest){
        User user = new User(UUID.randomUUID().toString(),
                userCreateRequest.getUserName(),
                userCreateRequest.getPassword(),
                userCreateRequest.getEmail(),
                userCreateRequest.getFirstName(),
                userCreateRequest.getLastName(),
                userCreateRequest.getUserType());
        userService.addNewUser(user);
        UserResponse userResponse = createUserResponse(user);

        return ResponseEntity.created(URI.create("/users" + user.getUserID())).body(userResponse);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequest){
        User user = new User(userUpdateRequest.getUserId(),
                userUpdateRequest.getUserName(),
                userUpdateRequest.getPassword(),
                userUpdateRequest.getEmail(),
                userUpdateRequest.getFirstName(),
                userUpdateRequest.getLastName(),
                userUpdateRequest.getUserType());
        user.setEventsList(userUpdateRequest.getEventsList());
        user.setNotifications(userUpdateRequest.getNotifications());

        UserResponse userResponse = createUserResponse(user);

        return ResponseEntity.ok(userResponse);

    }

    @DeleteMapping
    public ResponseEntity deleteUserById(@PathVariable("userId")String userId){
        userService.deleteUser(userId);
        return  ResponseEntity.noContent().build();
    }



    private UserResponse createUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserName(user.getUserName());
        userResponse.setUserType(user.getUserType());
        userResponse.setEmail(user.getEmail());
        userResponse.setEventsList(user.getEventsList());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEventsList(user.getEventsList());

        return userResponse;
    }
}
