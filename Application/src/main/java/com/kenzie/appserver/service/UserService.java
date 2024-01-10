package com.kenzie.appserver.service;


import com.kenzie.appserver.exception.EventNotFoundException;
import com.kenzie.appserver.exception.UserAlreadyExistsException;
import com.kenzie.appserver.exception.UserNotFoundException;
import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.NotificationData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    private EventRepository eventRepository;
    private LambdaServiceClient lambdaServiceClient;



    public UserService(UserRepository userRepository, EventRepository eventRepository, LambdaServiceClient lambdaServiceClient) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }
    public NotificationData getNotificationDataFromLambda(String notificationId) {
        return lambdaServiceClient.getNotificationData(notificationId);
    }
    public NotificationData setNotificationDataInLambda(String data) {
        return lambdaServiceClient.setNotificationData(data);
    }

    public UserRecord findUserById(String id) {
        Optional<UserRecord> user = userRepository.findById(id);

        if (user.isEmpty()) throw new UserNotFoundException("User " + id + " does not exist.");

        UserRecord userRecord = user.get();

        UserRecord ur = new UserRecord(userRecord.getUserID(), userRecord.getUserName(), userRecord.getPassword(),
                userRecord.getEventsList(), userRecord.getEmail(), userRecord.getFirstName(), userRecord.getLastName(),
                userRecord.getNotifications(), userRecord.getUserType(), userRecord.getFriends());

        return ur;
    }

    public boolean validateUser (String id, String password){
        Optional<UserRecord> user = userRepository.findById(id);
        if(user.isPresent()){
            UserRecord userRecord = user.get();
            return userRecord.getPassword().equals(password);
        }
        return false;
    }

    public List<UserRecord> getAllUsers() {
        List<UserRecord> users = new ArrayList<>();
        Iterable<UserRecord> pulledUsers = userRepository.findAll();

        for (UserRecord ur : pulledUsers) {
            UserRecord currentRecord = new UserRecord(ur.getUserID(), ur.getUserName(), ur.getPassword(),
                    ur.getEventsList(), ur.getEmail(), ur.getFirstName(), ur.getLastName(),
                    ur.getNotifications(), ur.getUserType(), ur.getFriends());

            users.add(currentRecord);
        }

        return users;
    }

    public UserRecord addNewUser(String userName, String password, String email, String firstName, String lastName, String userType) {
        UserRecord ur = new UserRecord(userName, password, email, firstName, lastName, userType);
        Optional<UserRecord> check = userRepository.findById(ur.getUserName());
        if(check.isPresent()){
            throw new UserAlreadyExistsException("User " + userName + " already exists.");
        }
        userRepository.save(ur);
        return ur;
    }


    public void addEventToList(String userId, String eventId){
        Optional<UserRecord> ur = userRepository.findById(userId);
        Optional<EventRecord> er = eventRepository.findById(eventId);
        if(ur.isEmpty() || er.isEmpty()){
            if(ur.isEmpty()){
                throw new UserNotFoundException("User " + userId +" does not exist.");
            }
            else{
                throw new EventNotFoundException("Event " + eventId + " does not exist.");
            }
        }
        UserRecord userRecord = ur.get();
        EventRecord eventRecord = er.get();
        if(userRecord.getEventsList().contains(eventId)){
            return;
        }
        userRecord.getEventsList().add(eventId);
        eventRecord.getPeopleAttending().add(userId);
        userRepository.save(userRecord);
        eventRepository.save(eventRecord);
    }

    public void updateUser(User user) {
        if (userRepository.existsById(user.getUserId())) {
            UserRecord ur = new UserRecord(user.getUserId(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEventsList(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getNotifications(),
                    user.getUserType(),
                    user.getFriends());

            userRepository.save(ur);
        }
    }


    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }

    public void addFriend(String userId, String friendId){
        System.out.println(userId + " " + friendId);
        Optional<UserRecord> ur = userRepository.findById(userId);
        Optional<UserRecord> fr = userRepository.findById(friendId);
        if(ur.isPresent() && fr.isPresent()){
            UserRecord user = ur.get();
            UserRecord friend = fr.get();
            if(user.getFriends().contains(friendId)){
                return;
            }
            user.getFriends().add(friendId);
            friend.getFriends().add(userId);
            userRepository.save(user);
            userRepository.save(friend);
            return;
        }
        throw new UserNotFoundException("User does not exist.");
    }

    public void shareEventWithFriend(String userId, String eventId) {
        Optional<UserRecord> userRecord = userRepository.findById(userId);
        if(userRecord.isPresent()){
            UserRecord user = userRecord.get();
            user.getNotifications().add("Event " + eventId + " shared");
            userRepository.save(user);
            return;
        }
        throw new UserNotFoundException("User " + userId + " does not exist.");

    }


    public List<String> viewFriendsEvents(String userId) {
        UserRecord user = findUserById(userId);
        if(user == null){
            throw new UserNotFoundException("User " + userId + " does not exist.");
        }
        System.out.println(userId);
        return user.getEventsList();
    }



}


