package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.NotificationDao;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.model.ExampleRecord;
import com.kenzie.capstone.service.model.NotificationData;
import com.kenzie.capstone.service.model.NotificationRecord;

import javax.inject.Inject;

import java.util.List;
import java.util.UUID;

public class LambdaService {

    private NotificationDao notificationDao;

    @Inject
    public LambdaService(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    public NotificationData getNotificationData(String id) {
        List<NotificationRecord> records = notificationDao.getNotificationData(id);
        if (records.size() > 0) {
            return new NotificationData(records.get(0).getId(), records.get(0).getData());
        }
        return null;
    }

    public NotificationData setNotificationData(String data) {
        String id = UUID.randomUUID().toString();
        NotificationRecord record = notificationDao.setNotificationData(id, data);
        return new NotificationData(id, data);
    }
}
