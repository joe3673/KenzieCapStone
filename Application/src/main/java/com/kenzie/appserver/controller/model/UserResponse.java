package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

public class UserResponse{

    @JsonProperty("userID")
    public String userID;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("eventsList")
    private List<String> eventsList;

    @JsonProperty("email")
    private String email;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("notifications")
    private List<String> notifications;

    @JsonProperty("userType")
    private String userType;

    @JsonProperty("friends")
    private List<String> friends;

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<String> eventsList) {
        this.eventsList = eventsList;
    }

    public String getEmail() {
        return email;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<String> getFriends(){
        return friends;
    }

    public void setFriends(List<String> friends){
        this.friends = friends;
    }
}

