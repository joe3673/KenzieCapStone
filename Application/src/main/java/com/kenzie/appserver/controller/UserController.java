package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.EventResponse;
import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.controller.model.UserUpdateRequest;
import com.kenzie.appserver.exception.EventNotFoundException;
import com.kenzie.appserver.exception.UserAlreadyExistsException;
import com.kenzie.appserver.exception.UserNotFoundException;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.EventService;
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

    private final EventService eventService;

    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") String userId) {
        UserRecord user;
        try{
            user = userService.findUserById(userId);
        }
        catch (UserNotFoundException ex){
            return ResponseEntity.notFound().build();
        }

        UserResponse userResponse = createUserResponseFromRecord(user);
        return ResponseEntity.ok(userResponse);
    }


    @PostMapping("/{userName}")
    public ResponseEntity<UserResponse> loginUser(@PathVariable("userName") String userName, @RequestBody String password){
        if(userService.validateUser(userName, password)){
            return getUserById(userName);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserRecord> users = userService.getAllUsers();
        List<UserResponse> responses = new ArrayList<>();
        for (UserRecord user : users) {
            responses.add(createUserResponseFromRecord(user));
        }
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody UserCreateRequest userCreateRequest) {
        try {
            UserRecord user = userService.addNewUser(userCreateRequest.getUserName(), userCreateRequest.getPassword(), userCreateRequest.getEmail(), userCreateRequest.getFirstName(), userCreateRequest.getLastName(), userCreateRequest.getUserType());
            UserResponse userResponse = createUserResponseFromRecord(user);
            return ResponseEntity.created(URI.create("/users/" + userResponse.getUserID())).body(userResponse);
        }
        catch (UserAlreadyExistsException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    /*
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
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/EventList")
    public ResponseEntity<Void> joinEvent(@PathVariable("userId") String userId, @RequestBody String eventId){
        try{
            userService.addEventToList(userId, eventId);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex){
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{userId}/FriendList")
    public ResponseEntity<Void> addFriend(@PathVariable("userId") String userId, @RequestBody String friendId){
        try {
            userService.addFriend(userId, friendId);
            return ResponseEntity.ok().build();
        }
        catch (UserNotFoundException ex){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> shareEventsWithFriend(@PathVariable("userId") String userId,
                                                       @RequestBody String eventId){
        try{
            userService.shareEventWithFriend(userId, eventId);
            return ResponseEntity.ok().build();
        }
        catch(UserNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/FriendList/Events")
    public ResponseEntity<List<EventResponse>> getEventsAttendedByFriends(@PathVariable("userId") String userId) {
        List<String> eventsAttendedByFriends = new ArrayList<>();
        try {
            eventsAttendedByFriends = userService.viewFriendsEvents(userId);
        }
        catch (UserNotFoundException ex){
            return ResponseEntity.badRequest().build();
        }

        List<Event> events = new ArrayList<>(eventsAttendedByFriends.size());
        for(int i = 0, size = eventsAttendedByFriends.size(); i < size; ++i){
            try{
                events.add(eventService.findByEventId(eventsAttendedByFriends.get(i)));
            }
            catch(EventNotFoundException ex){

            }
        }

        List<EventResponse> responses = new ArrayList<>();
        for (Event event : events) {
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
        userResponse.setFriends(user.getFriends());
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
        userResponse.setFriends(user.getFriends());
        return userResponse;
    }

    private  EventResponse createEventResponse(Event event) {
        EventResponse eventResponse = new EventResponse();
//        Set other event properties?
        return eventResponse;
    }
}
