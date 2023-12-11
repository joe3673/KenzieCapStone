package com.kenzie.appserver.service.model;

import java.util.List;

public class User {

    private final String userID;

    private final String userName;

    private final String password;

    private final List<Event> eventsList;


    public User(String userID, String userName, String password, List<Event> eventsList) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.eventsList = eventsList;
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

    public List<Event> getEventsList() {
        return eventsList;
    }


}
