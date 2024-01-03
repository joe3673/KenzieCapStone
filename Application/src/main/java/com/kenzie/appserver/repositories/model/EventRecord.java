package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;


import java.time.LocalDateTime;
import java.util.List;

public class EventRecord{

    private final String eventID;
    private String name;
    private String location;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private List<String> peopleAttending;

    private List<String> peopleAttended;

    private  String eventSponsor;


    public EventRecord(String eventID, String name, String location, LocalDateTime startTime, LocalDateTime endTime, List<String> peopleAttending, List<String> peopleAttended, String eventSponsor){
        this.eventID = eventID;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.peopleAttending = peopleAttending;
        this.peopleAttended = peopleAttended;
        this.eventSponsor = eventSponsor;
    }

    @DynamoDBHashKey(attributeName = "id")
    public String getEventID() {
        return eventID;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "Location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBAttribute(attributeName = "Start_Time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @DynamoDBAttribute(attributeName = "End_Time")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @DynamoDBAttribute(attributeName = "People_Attending")
    public List<String> getPeopleAttending() {
        return peopleAttending;
    }

    public void setPeopleAttending(List<String> peopleAttending) {
        this.peopleAttending = peopleAttending;
    }

    @DynamoDBAttribute(attributeName = "People_Attended")
    public List<String> getPeopleAttended() {
        return peopleAttended;
    }

    public void setPeopleAttended(List<String> peopleAttended) {
        this.peopleAttended = peopleAttended;
    }

    @DynamoDBAttribute(attributeName = "Event_Sponsor")
    public String getEventSponsor() {
        return eventSponsor;
    }

    public void setEventSponsor(String eventSponsor) {
        this.eventSponsor = eventSponsor;
    }
}
