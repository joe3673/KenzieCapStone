package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "Organization")
public class OrganizationRecord {

    private  String organizationName;

    private  List<String> eventsHeldList;


    public OrganizationRecord(String organizationName, List<String> eventsHeldList){

        this.organizationName = organizationName;
        this.eventsHeldList = eventsHeldList;
    }

    @DynamoDBHashKey(attributeName = "ID")
    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @DynamoDBAttribute(attributeName = "EventsHeld")
    public List<String> getEventsHeldList() {
        return eventsHeldList;
    }

    public void setEventsHeldList(List<String> eventsHeldList) {
        this.eventsHeldList = eventsHeldList;
    }
}
