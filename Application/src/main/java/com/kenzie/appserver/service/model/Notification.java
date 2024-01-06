package com.kenzie.appserver.service.model;

public class Notification {

    private final String notificationId;

    private final String text;


    public Notification(String notificationId, String text) {
        this.notificationId = notificationId;
        this.text = text;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getText() {
        return text;
    }
}
