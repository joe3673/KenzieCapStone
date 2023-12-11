package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.time.LocalDateTime;
import java.util.List;


public class EventResponse{


    @JsonProperty("name")
    private String name;

    @JsonProperty("location")
    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("startTime")
    private LocalDateTime startTime;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("endTime")
    private LocalDateTime endTime;

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


    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime(){
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

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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

