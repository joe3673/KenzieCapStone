package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository){

        this.userRepository = userRepository;
    }
    public List<User> findAllUsers(){
        List<User> users = new ArrayList<>();


        Iterable<UserRecord> userRecordIterator = userRepository.findAll();
        for (UserRecord record : userRecordIterator){
            User user = new User(
                    record.getUserID(),
                    record.getUserName(),
                    record.getPassword(),
                    record.getEmail(),
                    record.getFirstName(),
                    record.getLastName(),
                    record.getUserType());
            user.setNotifications(record.getNotifications());
            user.setEventsList(record.getEventsList());
        }
        return users;
    }

    public User findByUserId (String userId){
        Optional<UserRecord> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            UserRecord userRecord = optionalUser.get();
            User user = new User(
                    userRecord.getUserID(),
                    userRecord.getUserName(),
                    userRecord.getPassword(),
                    userRecord.getEmail(),
                    userRecord.getFirstName(),
                    userRecord.getLastName(),
                    userRecord.getUserType()
            );

            // Set events and notifications directly from the UserRecord
            user.setEventsList(userRecord.getEventsList());
            user.setNotifications(userRecord.getNotifications());

            return user;
        }

        return null; // User not found
    }

    public User addNewUser(User user){

        UserRecord userRecord = new UserRecord(user.getUserID(),
                user.getUserID(), user.getPassword(),
                user.getEventsList(),user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getNotifications(),
                user.getUserType());
        userRepository.save(userRecord);
        return user;
    }

    public void updateUser(User user){
        if(userRepository.existsById(user.getUserID())){
            UserRecord userRecord = new UserRecord(
                    user.getUserID(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEventsList(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getNotifications(),
                    user.getUserType());

            userRepository.save(userRecord);
        }
    }


    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }




}
