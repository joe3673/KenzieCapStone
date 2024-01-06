package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.EventCreateRequest;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.*;


@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private QueryUtility queryUtility;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final MockNeat mockNeat = MockNeat.threadLocal();

    @BeforeAll
    public void setup(){
        queryUtility = new QueryUtility(mockMvc);
    }



    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        // Given
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setPassword("f");
        userCreateRequest.setUserName(mockNeat.users().get());
        queryUtility.userControllerClient.addUser(userCreateRequest);

        // When & Then
        queryUtility.userControllerClient.getUserById(userCreateRequest.getUserName())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").isNotEmpty());
    }
    @Test
    void getUserById_WhenUserNotFound_ShouldReturnNotFound() throws Exception {
        // Given
        String userId = "non-existent-id";


        // When & Then
        queryUtility.userControllerClient.getUserById(userId)
                .andExpect(status().isNotFound());
    }

    @Test
    void addUser_UserAlreadyExists_ShouldReturnBadRequest() throws Exception {
        // Given
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setPassword("f");
        userCreateRequest.setUserName(mockNeat.users().get());
        queryUtility.userControllerClient.addUser(userCreateRequest);


        // When & Then
        queryUtility.userControllerClient.addUser(userCreateRequest)
                .andExpect(status().isBadRequest());
    }
    @Test
    void deleteUserById_ShouldDeleteUser() throws Exception {
        // Given
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setPassword("f");
        userCreateRequest.setUserName(mockNeat.users().get());
        queryUtility.userControllerClient.addUser(userCreateRequest);

        // When & Then
        queryUtility.userControllerClient.deleteUserById(userCreateRequest.getUserName())
                .andExpect(status().isOk());
    }

    @Test
    void addUser_ShouldCreateUser() throws Exception {
        // Given
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setPassword("f");
        userCreateRequest.setUserName(mockNeat.users().get());


        // When & Then
        queryUtility.userControllerClient.addUser(userCreateRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userID").isNotEmpty());
    }
    @Test
    void loginUser_WhenValidCredentials_ShouldReturnUser() throws Exception {
        // Given
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setPassword("f");
        userCreateRequest.setUserName(mockNeat.users().get());
        queryUtility.userControllerClient.addUser(userCreateRequest);
        // When & Then
        queryUtility.userControllerClient.loginUser(userCreateRequest.getUserName(), userCreateRequest.getPassword())
                .andExpect(status().isOk());
    }
    @Test
    void joinEvent_WhenEventExists_ShouldJoinEvent() throws Exception {
        // Given
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setPassword("f");
        userCreateRequest.setUserName(mockNeat.users().get());
        queryUtility.userControllerClient.addUser(userCreateRequest);

        EventCreateRequest request = new EventCreateRequest();
        request.setEventSponsor("f");
        request.setLocation("f");
        request.setName("f");
        request.setEndTime(LocalDateTime.now().plusDays(3).toString());
        request.setStartTime(LocalDateTime.now().toString());
        ResultActions temp = queryUtility.eventControllerClient.addNewEvent(request);
        EventResponse eventResponse = objectMapper.readValue(temp.andReturn().getResponse().getContentAsString(), EventResponse.class);

        // When & Then
        queryUtility.userControllerClient.joinEvent(userCreateRequest.getUserName(), eventResponse.getEventId())
                .andExpect(status().isOk());
    }

    @Test
    void getAllUsers_ShouldReturnUserList() throws Exception {
        // GIVEN


        // WHEN


        // THEN
        queryUtility.userControllerClient.getAllUsers()
                .andExpect(status().isOk());

    }



    @Test
    void shareEventsWithFriend_ShouldReturnOk() throws Exception {
        // GIVEN
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setPassword("f");
        userCreateRequest.setUserName(mockNeat.users().get());
        queryUtility.userControllerClient.addUser(userCreateRequest);

        EventCreateRequest request = new EventCreateRequest();
        request.setEventSponsor("f");
        request.setLocation("f");
        request.setName("f");
        request.setEndTime(LocalDateTime.now().plusDays(3).toString());
        request.setStartTime(LocalDateTime.now().toString());
        ResultActions temp = queryUtility.eventControllerClient.addNewEvent(request);
        EventResponse eventResponse = objectMapper.readValue(temp.andReturn().getResponse().getContentAsString(), EventResponse.class);

        // WHEN

        // THEN
        queryUtility.userControllerClient.shareEventsWithFriend(userCreateRequest.getUserName(), eventResponse.getEventId())
            .andExpect(status().isOk());
    }

    @Test
    void getEventsAttendedByFriends_ShouldReturnEvents() throws Exception {
        // GIVEN
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setPassword("f");
        userCreateRequest.setUserName(mockNeat.users().get());
        queryUtility.userControllerClient.addUser(userCreateRequest);

        // WHEN


        // THEN
        queryUtility.userControllerClient.getEventsAttendedByFriends(userCreateRequest.getUserName())
                .andExpect(status().isOk());
    }


}
