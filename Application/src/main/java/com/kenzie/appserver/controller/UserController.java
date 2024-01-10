package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.exception.EventNotFoundException;
import com.kenzie.appserver.exception.UserAlreadyExistsException;
import com.kenzie.appserver.exception.UserNotFoundException;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.EventService;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.model.NotificationData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    private final UserService userService;

    private final EventService eventService;


    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }
    @PutMapping("/{userId}/notification")
    public ResponseEntity<NotificationData> getUserNotification(@PathVariable("userId") String userId, @RequestBody GetNotificationRequest getNotificationRequest) {
        return ResponseEntity.ok(userService.getNotificationDataFromLambda(getNotificationRequest.getNotificationId()));
    }
    @PostMapping("/{userId}/notification")
    public ResponseEntity<NotificationData> setUserNotification(@PathVariable("userId") String userId, @RequestBody AddNotificationRequest addNotificationRequest) {
       return ResponseEntity.ok(userService.setNotificationDataInLambda(addNotificationRequest.getData()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") String userId) {
        try {
            UserRecord user = userService.findUserById(userId);
            UserResponse userResponse = createUserResponseFromRecord(user);
            return ResponseEntity.ok(userResponse);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserCreateRequest userCreateRequest) {
        try {
            if (userService.validateUser(userCreateRequest.getUserName(), userCreateRequest.getPassword())) {
                return ResponseEntity.ok(getUserById(userCreateRequest.getUserName()).getBody());
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (UserNotFoundException ex) {
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
            return ResponseEntity.created(URI.create("/User/" + userResponse.getUserID())).body(userResponse);
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
    public ResponseEntity<Void> joinEvent(@PathVariable("userId") String userId, @RequestBody JoinEventRequest joinEventRequest){
        try {
            userService.addEventToList(userId, joinEventRequest.getEventId());
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{userId}/FriendList")
    public ResponseEntity<Void> addFriend(@PathVariable("userId") String userId, @RequestBody AddFriendRequest addFriendRequest){
        try {
            userService.addFriend(userId, addFriendRequest.getFriendId());
            return ResponseEntity.ok().build();
        }
        catch (UserNotFoundException ex){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> shareEventsWithFriend(@PathVariable("userId") String userId, @RequestBody String eventId) {
        try {
            userService.shareEventWithFriend(userId, eventId);
            return ResponseEntity.ok().build();
        }
        catch (UserNotFoundException | EventNotFoundException ex){
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
                System.out.println(eventsAttendedByFriends.get(i));
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
        userResponse.setEventsList(user.getEventsList());
        return userResponse;
    }

    private UserResponse createUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserID(user.getUserId());
        userResponse.setUserName(user.getUserName());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setNotifications(user.getNotifications());
        userResponse.setUserType(user.getUserType());
        userResponse.setFriends(user.getFriends());
        userResponse.setEventsList(user.getEventsList());
        return userResponse;
    }

    private EventResponse createEventResponse(Event event) {
        EventResponse eventResponse = new EventResponse();
        eventResponse.setEventId(event.getEventID());
        eventResponse.setName(event.getName());
        eventResponse.setLocation(event.getLocation());
        eventResponse.setStartTime(event.getStartTime());
        eventResponse.setEndTime(event.getEndTime());
        eventResponse.setPeopleAttending(event.getPeopleAttending());
        eventResponse.setPeopleAttended(event.getPeopleAttended());
        eventResponse.setEventSponsor(event.getEventSponsor());
        return eventResponse;
    }
}
