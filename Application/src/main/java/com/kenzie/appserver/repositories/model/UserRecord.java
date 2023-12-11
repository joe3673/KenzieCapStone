package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.kenzie.appserver.service.model.Event;

import java.util.List;

public class UserRecord{
    private final String userID;
    private String userName;
    private String password;
    private List<String> eventsList;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> notifications;
    private String userType;


    public UserRecord(String userID, String userName, String password, List<String> eventsList, String email, String firstName, String lastName, List<String> notifications, String userType) {
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

    @DynamoDBHashKey(attributeName = "ID")
    public String getUserID(){
        return userID;
    }

    @DynamoDBAttribute(attributeName = "Username")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    @DynamoDBAttribute(attributeName = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    @DynamoDBAttribute(attributeName = "EventList")
    public List<String> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<String> eventsList){
        this.eventsList = eventsList;
    }

    @DynamoDBAttribute(attributeName = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(){
        this.email = email;
    }


    @DynamoDBAttribute(attributeName = "First_Name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    @DynamoDBAttribute(attributeName = "Last_Name")
    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    @DynamoDBAttribute(attributeName = "Notifications")
    public List<String> getNotifications(){
        return notifications;
    }

    public void setNotifications(List<String> notifications){
        this.notifications = notifications;
    }

    @DynamoDBAttribute(attributeName = "User_Type")
    public String getUserType(){
        return userType;
    }

    public void setUserType(String userType){
        this.userType = userType;
    }


}
