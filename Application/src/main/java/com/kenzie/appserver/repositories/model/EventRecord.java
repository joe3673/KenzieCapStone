package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


import java.util.List;

@DynamoDBTable(tableName = "Events")

public class EventRecord{

    private String eventID;
    private String name;
    private String location;

    private String startTime;


    private String endTime;

    private List<String> peopleAttending;

    private List<String> peopleAttended;

    private  String eventSponsor;



    public EventRecord(String eventID, String name, String location, String startTime, String endTime, List<String> peopleAttending, List<String> peopleAttended, String eventSponsor){
        this.eventID = eventID;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.peopleAttending = peopleAttending;
        this.peopleAttended = peopleAttended;
        this.eventSponsor = eventSponsor;
    }

    public EventRecord(){

    }

    @DynamoDBHashKey(attributeName = "id")
    public String getEventID() {
        return eventID;
    }
    
    public void setEventID(String eventID){
        this.eventID = eventID;
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
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @DynamoDBAttribute(attributeName = "End_Time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
