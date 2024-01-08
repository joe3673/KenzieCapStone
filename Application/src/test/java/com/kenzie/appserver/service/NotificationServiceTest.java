package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.appserver.service.model.Notification;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.NotificationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Not;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class NotificationServiceTest{

    @Mock
    private LambdaServiceClient lambdaServiceClient;

    @InjectMocks
    private NotificationService notificationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById_ReturnsNotification() {
        // GIVEN
        NotificationData data = new NotificationData("l", "l");
        when(lambdaServiceClient.getNotificationData("l")).thenReturn(data);

        // WHEN
        Notification notification = notificationService.findById("l");

        // THEN
        assertEquals(data.getId(), notification.getNotificationId());
        assertEquals(data.getData(), notification.getText());
    }

    @Test
    void createNewNotification_ReturnsNotification(){
        // GIVEN
        NotificationData data = new NotificationData("l", "m");
        when(lambdaServiceClient.setNotificationData("m")).thenReturn(data);

        // WHEN
        Notification notification = notificationService.createNewNotification("m");

        // THEN
        assertEquals(data.getId(), notification.getNotificationId());
        assertEquals(data.getData(), notification.getText());
    }

}
