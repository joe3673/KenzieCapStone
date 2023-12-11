package com.kenzie.appserver.controller.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.Event;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UserCreateRequest{

    @NotEmpty
    @JsonProperty("id")
    private String userID;

    @NotEmpty
    @JsonProperty("username")
    private String userName;

    @NotEmpty
    @JsonProperty("password")
    private String password;

    @JsonProperty("eventsList")
    private List<Event> eventsList;


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
