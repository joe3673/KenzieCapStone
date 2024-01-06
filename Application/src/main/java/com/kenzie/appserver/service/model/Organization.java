package com.kenzie.appserver.service.model;

import java.util.List;

public class Organization {

    private final String organizationName;

    private final List<String> eventsHeldList;

    public Organization(String organizationName, List<String> eventsHeldList) {

        this.organizationName = organizationName;
        this.eventsHeldList = eventsHeldList;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public List<String> getOrganizationEventCount() {
        return eventsHeldList;
    }


}
