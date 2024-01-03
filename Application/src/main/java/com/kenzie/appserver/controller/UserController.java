package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.EventResponse;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.controller.model.UserUpdateRequest;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/User")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") String userId) {
        UserRecord user = userService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserResponse userResponse = createUserResponseFromRecord(user);
        return ResponseEntity.ok(userResponse);
    }


    @PostMapping
    public ResponseEntity<UserResponse> loginUser(@RequestBody String userName, @RequestBody String password){
        if (userService.validateUser(userName, password)){
            return getUserById(userName);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserRecord> users = userService.getAllUsers();

        if (users == null || users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<UserResponse> responses = new ArrayList<>();
        for (UserRecord user : users) {
            responses.add(createUserResponseFromRecord(user));
        }
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody UserCreateRequest userCreateRequest) {
        UserRecord user = new UserRecord(
                userCreateRequest.getUserName(),
                userCreateRequest.getUserName(),
                userCreateRequest.getPassword(),
                userCreateRequest.getEventsList(),
                userCreateRequest.getEmail(),
                userCreateRequest.getFirstName(),
                userCreateRequest.getLastName(),
                userCreateRequest.getNotifications(),
                userCreateRequest.getUserType()
        );

        userService.addNewUser(user.getFirstName(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getUserType());

        UserResponse userResponse = createUserResponseFromRecord(user);
        return ResponseEntity.created(URI.create("/users/" + userResponse.getUserID())).body(userResponse);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        User user = new User(
                userUpdateRequest.getUserId(),
                userUpdateRequest.getUserName(),
                userUpdateRequest.getPassword(),
                userUpdateRequest.getEventsList(),
                userUpdateRequest.getEmail(),
                userUpdateRequest.getFirstName(),
                userUpdateRequest.getLastName(),
                userUpdateRequest.getNotifications(),
                userUpdateRequest.getUserType()
        );

        userService.updateUser(user);

        UserResponse userResponse = createUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }
    */

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> shareEventsWithFriends(@PathVariable("userId") String userId,
                                                       @PathVariable("eventId") String eventId) {

        boolean sharingSuccessful = userService.shareEventsWithFriends(userId, eventId);

        if (sharingSuccessful) {
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<List<EventResponse>> getEventsAttendedByFriends(@PathVariable("userId") String userId) {
        List<Event> eventsAttendedByFriends = userService.getEventEventsAttendedByFriends(userId);

        if (eventsAttendedByFriends == null || eventsAttendedByFriends.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EventResponse> responses = new ArrayList<>();
        for (Event event : eventsAttendedByFriends) {
            responses.add(createEventResponse(event));
        }
        return ResponseEntity.ok(responses);
    }

    private UserResponse createUserResponseFromRecord(UserRecord user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserID(user.getUserID());
        userResponse.setUserName(user.getUserName());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setNotifications(user.getNotifications());
        userResponse.setUserType(user.getUserType());
        return userResponse;
    }
  
    private UserResponse createUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserID(user.getUserID());
        userResponse.setUserName(user.getUserName());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setNotifications(user.getNotifications());
        userResponse.setUserType(user.getUserType());
        return userResponse;
    }

    private  EventResponse createEventResponse(Event event) {
        EventResponse eventResponse = new EventResponse();
//        Set other event properties?
        return eventResponse;
    }
}
