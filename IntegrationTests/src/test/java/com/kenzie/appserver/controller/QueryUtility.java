package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
public class QueryUtility{


    public class EventControllerClient{
        public ResultActions getEventById(String eventId) throws Exception{
            return mvc.perform(get("/Event/{eventId}", eventId).accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions getAllEvents() throws Exception{
            return mvc.perform(get("/Event/all").accept(MediaType.APPLICATION_JSON));
        }
        public ResultActions addNewEvent(EventCreateRequest eventCreateRequest) throws Exception {
            return mvc.perform(post("/Event/").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(eventCreateRequest)));
        }
        public ResultActions deleteEventById(String eventId) throws Exception{
            return mvc.perform(delete("/Event/{eventId}", eventId).accept(MediaType.APPLICATION_JSON));
        }


    }

    public EventControllerClient eventControllerClient;
    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public QueryUtility(MockMvc mvc) {
        this.mvc = mvc;
        this.eventControllerClient = new EventControllerClient();
    }



}


