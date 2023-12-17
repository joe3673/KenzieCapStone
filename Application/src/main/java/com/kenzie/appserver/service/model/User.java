package com.kenzie.appserver.service.model;

import java.util.List;

public class User{

    private final String userID;
    private final String userName;
    private final String password;
    private List<String> eventsList;
    private final String email;
    private final String firstName;
    private final String lastName;
    private  List<String> notifications;
    private final String userType;


    public User(String userID, String userName, String password, String email, String firstName, String lastName, String userType) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public void setEventsList(List<String> eventsList) {
        this.eventsList = eventsList;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }
}
