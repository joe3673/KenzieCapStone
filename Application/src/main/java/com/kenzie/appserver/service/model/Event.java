package com.kenzie.appserver.service.model;

import java.util.ArrayList;
import java.util.List;

public class Event{
   private final String eventID;
   private final String name;
   private final String location;
   private final String startTime;

   private final String endTime;

   private final List<String> peopleAttending;

   private final List<String> peopleAttended;

   private final String eventSponsor;

   
    public Event(String eventID, String name, String location, String startTime, String endTime, List<String> peopleAttending, List<String> peopleAttended, String eventSponsor){
        this.eventID = eventID;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.peopleAttending = peopleAttending;
        this.peopleAttended = peopleAttended;
        this.eventSponsor = eventSponsor;
    }

    public Event(String eventID, String name, String location, String startTime, String endTime, String eventSponsor){
        this.eventID = eventID;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.peopleAttending = new ArrayList<>();
        this.peopleAttended = new ArrayList<>();
        this.eventSponsor = eventSponsor;
    }
    public String getEventID() {
        return eventID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
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


}


