package com.kenzie.appserver.service.model;

import org.apache.tomcat.jni.Local;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class Event {
   private  final String eventID;
   private final String name;
   private final String location;
   private final LocalDateTime date;



    public Event(String eventID, String name, String location, LocalDateTime date) {
        this.eventID = eventID;
        this.name = name;
        this.location = location;
        this.date = date;



    }

    public String getEventID(){
        return eventID;
    }
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
