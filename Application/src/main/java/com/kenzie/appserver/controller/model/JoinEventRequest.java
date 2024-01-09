package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinEventRequest {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("eventId")
    private String eventId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
