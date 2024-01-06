package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.NotificationData;
import com.kenzie.capstone.service.model.NotificationRecord;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationDao {

    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     *
     * @param mapper Access to DynamoDB
     */
    public NotificationDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public NotificationData storeNotificationData(NotificationData notificationData) {
        try {
            mapper.save(notificationData, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id has already been used");
        }

        return notificationData;
    }

    public List<NotificationRecord> getNotificationData(String id) {
        NotificationRecord exampleRecord = new NotificationRecord();
        exampleRecord.setId(id);

        DynamoDBQueryExpression<NotificationRecord> queryExpression = new DynamoDBQueryExpression<NotificationRecord>()
                .withHashKeyValues(exampleRecord)
                .withConsistentRead(false);

        return mapper.query(NotificationRecord.class, queryExpression);
    }

    public NotificationRecord setNotificationData(String id, String data) {
        NotificationRecord notificationRecord = new NotificationRecord();
        notificationRecord.setId(id);
        notificationRecord.setData(data);

        try {
            mapper.save(notificationRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id already exists");
        }

        return notificationRecord;
    }

}

