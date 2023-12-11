package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class EventRecord{

    private String eventID;
    private String name;
    private String location;
    private LocalDateTime date;




    public EventRecord(String eventID, String name, String location, LocalDateTime date, Timestamp timestamp) {
        this.eventID = eventID;
        this.name = name;
        this.location = location;
        this.date = date;
    }
    @DynamoDBHashKey(attributeName = "ID")
    public String getEventID(){
        return eventID;
    }
    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }
    @DynamoDBAttribute(attributeName = "Location")
    public String getLocation() {
        return location;
    }

    @DynamoDBAttribute(attributeName = "Date")
    public LocalDateTime getDate() {
        return date;
    }



    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
