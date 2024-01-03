package com.kenzie.appserver.service;


import com.kenzie.appserver.exception.UserNotFoundException;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.client.LambdaServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private UserRepository userRepository;
    private LambdaServiceClient lambdaServiceClient;

    public UserService(UserRepository userRepository, LambdaServiceClient lambdaServiceClient) {
        this.userRepository = userRepository;
        this.lambdaServiceClient = lambdaServiceClient;
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
        userRepository.save(ur);
        return ur;
    }

    public boolean shareEventsWithFriends(String userId, String eventId) {
        return true;
    }


    /*
    public void updateUser(User user) {
        if (userRepository.existsById(user.getUserID())) {
            UserRecord ur = new UserRecord(user.getUserID(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEventsList(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getNotifications(),
                    user.getUserType());

            userRepository.save(ur);
        }
    }
     */

    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }

    public void shareEventWithFriend(String userId, String eventId) {
        UserRecord user = findUserById(userId);

        List<String> friendsEvents = user.getEventsList();
        friendsEvents.add(eventId);

        user.setEventsList(friendsEvents);

        userRepository.save(user);
    }

    public List<String> viewFriendsEvents(String userId) {
        UserRecord user = findUserById(userId);
        if(user == null){
            throw new UserNotFoundException("User " + userId + " does not exist.");
        }
        return user.getEventsList();
    }



}



