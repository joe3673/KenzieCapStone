package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;


public class EventCreateRequest{



    @JsonProperty("name")
    private String name;

    @JsonProperty("location")
    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("startTime")
    private String startTime;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("endTime")
    private String endTime;

    @JsonProperty("peopleAttending")
    private List<String> peopleAttending;

    @JsonProperty("peopleAttended")
    private List<String> peopleAttended;

    @JsonProperty("eventSponsor")
    private String eventSponsor;




    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }


    public String getStartTime() {
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public List<String> getPeopleAttending() {
        return peopleAttending;
    }

    public List<String> getPeopleAttended() {
        return peopleAttended;
    }

    public String getEventSponsor() {
        return eventSponsor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setPeopleAttending(List<String> peopleAttending) {
        this.peopleAttending = peopleAttending;
    }

    public void setPeopleAttended(List<String> peopleAttended) {
        this.peopleAttended = peopleAttended;
    }

    public void setEventSponsor(String eventSponsor) {
        this.eventSponsor = eventSponsor;
    }
}

