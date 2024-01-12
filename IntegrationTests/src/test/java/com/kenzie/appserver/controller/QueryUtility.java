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

    public class UserControllerClient{
        public ResultActions getUserById(String userId) throws Exception{
            return mvc.perform(get("/User/{userId}", userId).accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions getAllUsers() throws Exception{
            return mvc.perform(get("/User/all").accept(MediaType.APPLICATION_JSON));
        }
        public ResultActions loginUser(UserCreateRequest userCreateRequest) throws Exception {
            return mvc.perform(post("/User/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userCreateRequest)));
        }

        public ResultActions addUser(UserCreateRequest userCreateRequest) throws Exception {
            return mvc.perform(post("/User/").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userCreateRequest)));
        }

        public ResultActions joinEvent(String userId, JoinEventRequest joinEventRequest) throws Exception {
            return mvc.perform(post("/User/{userId}/EventList", userId).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(joinEventRequest)));
        }

        public ResultActions addFriend(String userId, AddFriendRequest addFriendRequest) throws Exception {
            return mvc.perform(post("/User/{userId}/FriendList", userId).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(addFriendRequest)));
        }

        public ResultActions shareEventsWithFriend(String userId, String eventId) throws Exception {
            return mvc.perform(put("/User/{userId}", userId).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(eventId));
        }

        public ResultActions getEventsAttendedByFriends(String userId) throws Exception{
            return mvc.perform(get("/User/{userId}/FriendList/Events", userId).accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions deleteUserById(String userId) throws Exception{
            return mvc.perform(delete("/User/{userId}", userId).accept(MediaType.APPLICATION_JSON));
        }

    }

    public EventControllerClient eventControllerClient;

    public UserControllerClient userControllerClient;
    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public QueryUtility(MockMvc mvc) {
        this.mvc = mvc;
        this.eventControllerClient = new EventControllerClient();
        this.userControllerClient = new UserControllerClient();
    }



}


