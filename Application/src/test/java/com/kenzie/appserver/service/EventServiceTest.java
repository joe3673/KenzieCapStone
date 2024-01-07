package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private CacheClient cacheClient;
    @InjectMocks
    private EventService eventService;

    private EventRecord createMockEventRecord(String eventId, String eventName, String eventLocation, String startTime, String endTime, List<String> peopleAttending, List<String> peopleAttended, String eventSponsor) {
        return new EventRecord(eventId, eventName, eventLocation, startTime, endTime, peopleAttending,  peopleAttended, eventSponsor);

    }

    private UserRecord createMockUserRecord(String userId, String userName, String password, String email, String firstName, String lastName, String userType, List<String> eventsList, List<String> notifications, List<String> friends) {
        return new UserRecord(userId, userName, password, eventsList, email, firstName, lastName, notifications, userType, friends);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAllEvents_ReturnsListOfEvents() {
        // GIVEN
        EventRecord mockEventRecord1 = createMockEventRecord("event1", "Event One", "Location 1", LocalDateTime.now().toString(), LocalDateTime.now().plusHours(2).toString(), Arrays.asList("user1"), Arrays.asList("user2"), "Sponsor1");
        EventRecord mockEventRecord2 = createMockEventRecord("event2", "Event Two", "Location 2", LocalDateTime.now().toString(), LocalDateTime.now().plusHours(3).toString(), Arrays.asList("user3"), Arrays.asList("user4"), "Sponsor2");
        when(eventRepository.findAll()).thenReturn(Arrays.asList(mockEventRecord1, mockEventRecord2));

        // WHEN
        List<Event> events = eventService.findAllEvents();

        // THEN
        assertNotNull(events);
        assertEquals(2, events.size());
        assertEquals(mockEventRecord1.getEventID(), events.get(0).getEventID());
    }

    @Test
    void findByEventId_EventExists() {
        // GIVEN
        String eventId = "existingEventId";
        String eventName = "Event Name";
        String eventLocation = "Event Location";
        String startTime = LocalDateTime.now().toString();
        String endTime = LocalDateTime.now().plusHours(2).toString();
        List<String> peopleAttending = Arrays.asList("user1", "user2");
        List<String> peopleAttended = Arrays.asList("user3", "user4");
        String eventSponsor = "Sponsor Name";

        EventRecord mockEventRecord = new EventRecord(eventId, eventName, eventLocation, startTime, endTime, peopleAttending,  peopleAttended, eventSponsor);


        when(eventRepository.findById(eventId)).thenReturn(Optional.of(mockEventRecord));

        // WHEN
        Event event = eventService.findByEventId(eventId);

        // THEN
        assertNotNull(event);
        assertEquals(eventId, event.getEventID());
        assertEquals(eventName, event.getName());
        assertEquals(eventLocation, event.getLocation());
    }


    @Test
    void findByEventId_EventDoesNotExist() {
        // GIVEN
        String eventId = "nonExistingEventId";
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // WHEN
        Event event = eventService.findByEventId(eventId);

        // THEN
        assertNull(event);
    }

    @Test
    void addNewEvent_Successful() {
        // GIVEN
        String eventId = "event1";
        String eventName = "Event Name";
        String eventLocation = "Event Location";
        String startTime = LocalDateTime.now().toString();
        String endTime = LocalDateTime.now().plusHours(2).toString();
        List<String> peopleAttending = Arrays.asList("user1", "user2");
        List<String> peopleAttended = Arrays.asList("user3", "user4");
        String eventSponsor = "Sponsor Name";

        Event mockEvent = new Event(eventId, eventName, eventLocation, startTime, endTime, peopleAttending, peopleAttended, eventSponsor);

        EventRecord mockEventRecord = new EventRecord(eventId, eventName, eventLocation, startTime, endTime, peopleAttending, peopleAttended, eventSponsor);
        when(eventRepository.save(any(EventRecord.class))).thenReturn(mockEventRecord);

        // WHEN
        Event savedEvent = eventService.addNewEvent(mockEvent);

        // THEN
        assertNotNull(savedEvent);
        assertEquals(mockEvent.getEventID(), savedEvent.getEventID());
        assertEquals(eventName, savedEvent.getName());
        assertEquals(eventLocation, savedEvent.getLocation());

    }


    @Test
    void deleteEvent_EventExists() {
        // GIVEN
        String eventId = "existingEventId";
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        list1.add(eventId);
        list2.add("Notification1");
        list3.add("Friend1");
        EventRecord mockEventRecord = createMockEventRecord(eventId, "Event Name", "Location", LocalDateTime.now().toString(), LocalDateTime.now().plusHours(1).toString(), Arrays.asList("user1", "user2"), Arrays.asList("user3", "user4"), "Sponsor Name");
        UserRecord mockUserRecord = createMockUserRecord("user1", "UserOne", "password", "user1@example.com", "User", "One", "Regular", list1, list2, list3);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(mockEventRecord));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(mockUserRecord));

        // WHEN
        eventService.deleteEvent(eventId);

        // THEN
        verify(eventRepository).deleteById(eventId);
        verify(userRepository, atLeastOnce()).save(any(UserRecord.class));
        assertEquals(mockUserRecord.getEventsList().size(), 0);
    }

    @Test
    void deleteEvent_EventDoesNotExist() {
        // GIVEN
        String eventId = "nonExistingEventId";
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // WHEN
        eventService.deleteEvent(eventId);

        // THEN
        verify(eventRepository, never()).deleteById(anyString());
    }


}

