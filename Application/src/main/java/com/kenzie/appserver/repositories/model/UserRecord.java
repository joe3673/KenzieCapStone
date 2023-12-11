package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.kenzie.appserver.service.model.Event;

import java.util.List;

public class UserRecord{

    private final String userID;

    private String userName;

    private String password;

    private List<Event> eventsList;


    public UserRecord(String userID, String userName, String password, List<Event> eventsList) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.eventsList = eventsList;
    }
    @DynamoDBHashKey(attributeName = "ID")
    public String getUserID(){
        return userID;
    }
    @DynamoDBAttribute(attributeName = "Username")
    public String getUserName() {
        return userName;
    }
    @DynamoDBAttribute(attributeName = "Password")
    public String getPassword() {
        return password;
    }

    @DynamoDBAttribute(attributeName = "EventList")
    public List<Event> getEventsList() {
        return eventsList;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEventsList(List<Event> eventsList) {
        this.eventsList = eventsList;
    }
}
