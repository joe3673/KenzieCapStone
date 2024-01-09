package com.kenzie.appserver.controller;


import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.EventCreateRequest;
import com.kenzie.appserver.controller.model.EventUpdateRequest;
import com.kenzie.appserver.service.EventService;
import com.kenzie.appserver.service.model.Event;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.*;





@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class EventControllerTest{

    @Autowired
    private MockMvc mockMvc;

    
    private EventService eventService;


    private EventController eventController;

    private QueryUtility queryUtility;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String baseUrl = "/Event";


    @BeforeAll
    public void setup() {
        queryUtility = new QueryUtility(mockMvc);
    }

    @Test
    void whenDeleteEventById_thenEventIsDeleted() throws Exception {
        // Given
        EventCreateRequest request = new EventCreateRequest();
        request.setEventSponsor("f");
        request.setLocation("f");
        request.setName("f");
        request.setEndTime(LocalDateTime.now().plusDays(3).toString());
        request.setStartTime(LocalDateTime.now().toString());
        ResultActions temp = queryUtility.eventControllerClient.addNewEvent(request);
        EventResponse eventResponse = objectMapper.readValue(temp.andReturn().getResponse().getContentAsString(), EventResponse.class);
        // When
        ResultActions result = queryUtility.eventControllerClient.deleteEventById(eventResponse.getEventId());

        // Then
        result.andExpect(status().isOk());

    }


    @Test
    void whenGetAllEvents_thenAllEventsAreReturned() throws Exception {
        // Given

        // When


        // Then
        queryUtility.eventControllerClient.getAllEvents()
                        .andExpect(status().isOk());
    }

    @Test
    void whenAddNewEventWithValidData_thenEventIsCreated() throws Exception {
        // Given
        EventCreateRequest request = new EventCreateRequest();
        request.setEventSponsor("f");
        request.setLocation("f");
        request.setName("f");
        request.setEndTime(LocalDateTime.now().plusDays(3).toString());
        request.setStartTime(LocalDateTime.now().toString());


        // When


        // Then
        queryUtility.eventControllerClient.addNewEvent(request)
                        .andExpect(status().isCreated());

    }

    @Test
    void getEventById_EventExists() throws Exception {
        // Given
        EventCreateRequest request = new EventCreateRequest();
        request.setEventSponsor("f");
        request.setLocation("f");
        request.setName("f");
        request.setEndTime(LocalDateTime.now().plusDays(3).toString());
        request.setStartTime(LocalDateTime.now().toString());
        ResultActions temp = queryUtility.eventControllerClient.addNewEvent(request);
        EventResponse eventResponse = objectMapper.readValue(temp.andReturn().getResponse().getContentAsString(), EventResponse.class);

        // When & Then
        queryUtility.eventControllerClient.getEventById(eventResponse.getEventId())
                .andExpect(status().isOk());
    }

    @Test
    void getEventById_EventDoesNotExist() throws Exception {
        // Given
        String eventId = "nonExistingEventId";

        // When & Then
        queryUtility.eventControllerClient.getEventById(eventId)
                .andExpect(status().isNotFound());
    }




    //Still need get event and get event with invalid id.

    /*
    @Test
    void whenUpdateEventWithValidId_thenEventIsUpdated() throws Exception {
        // Given
        String validEventId = "valid-event-id";
        EventUpdateRequest request = new EventUpdateRequest();
        Event mockEvent = new Event();

        // When


        // Then
        mockMvc.perform(put(baseUrl + "/" + validEventId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(mockEvent.getEventID()));

    }

     */

    /*
    @Test
    void whenUpdateEventWithInvalidId_thenNotFound() throws Exception {
        // Given
        String invalidEventId = "invalid-event-id";
        EventUpdateRequest request = new EventUpdateRequest();

        //WHEN

        mockMvc.perform(put(baseUrl + "/" + invalidEventId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        // THEN
    }


     */

}

