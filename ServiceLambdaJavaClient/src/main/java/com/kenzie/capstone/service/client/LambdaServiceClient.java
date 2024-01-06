package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.NotificationData;


public class LambdaServiceClient {

    private static final String GET_NOTIFICATION_ENDPOINT = "notification/{id}";
    private static final String SET_NOTIFICATION_ENDPOINT = "notification";

    private ObjectMapper mapper;

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public NotificationData getNotificationData(String id) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_NOTIFICATION_ENDPOINT.replace("{id}", id));
        NotificationData notificationData;
        try {
            notificationData = mapper.readValue(response, NotificationData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return notificationData;
    }

    public NotificationData setNotificationData(String data) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_NOTIFICATION_ENDPOINT, data);
        NotificationData notificationData;
        try {
            notificationData = mapper.readValue(response, NotificationData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return notificationData;
    }
}
