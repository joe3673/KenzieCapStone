package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {
    private EventRepository eventRepository;
    private EventService eventService;

    @BeforeEach
    void setup() {
        eventRepository = mock(EventRepository.class);
        eventService = new EventService(eventRepository);
    }

    // TODO deleteEvent() unit tests
    @Test
    void deleteEvent_nullId_throwsException() {
        //GIVEN
        String nullId = null;

        //WHEN/THEN
        doThrow(new IllegalArgumentException()).when(eventRepository).deleteById(nullId);

        assertThrows(IllegalArgumentException.class, () -> eventService.deleteEvent(nullId));
    }

    @Test
    void deleteEvent_validId_deletesEvent() {
        //GIVEN
        String validId = "validId";

        //WHEN
        eventService.deleteEvent(validId);

        ArgumentCaptor<String> deleteArgument = ArgumentCaptor.forClass(String.class);
        verify(eventRepository, times(1)).deleteById(deleteArgument.capture());

        //THEN
        assertEquals(deleteArgument.getValue(), validId);
    }

}
