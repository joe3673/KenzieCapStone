package com.kenzie.appserver.service.model;

import java.util.List;

public class User{

    private final String userID;
    private final String userName;
    private final String password;
    private final List<String> eventsList;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final List<String> notifications;
    private final String userType;


    public User(String userID, String userName, String password, List<String> eventsList, String email, String firstName, String lastName, List<String> notifications, String userType) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.eventsList = eventsList;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.notifications = notifications;
        this.userType = userType;
    }

    public String getUserID(){
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getEventsList() {
        return eventsList;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public List<String> getNotifications(){
        return notifications;
    }

    public String getUserType(){
        return userType;
    }
}
