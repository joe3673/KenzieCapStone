package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.OrganizationRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.OrganizationRecord;
import com.kenzie.appserver.service.model.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class OrganizationServiceTest {
    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private OrganizationRecord mockOrganization;
    @InjectMocks
    private OrganizationService organizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void addNewOrganization_Successful() {
        // GIVEN
        String organizationName = "TestOrganization";
        List<String> eventsHeld = Arrays.asList("Event1", "Event2");

        // WHEN
        when(organizationRepository.save(any(OrganizationRecord.class))).thenReturn(mockOrganization);
        OrganizationRecord savedOrganization = organizationService.addNewOrganization(organizationName, eventsHeld);

        // THEN
        assertNotNull(savedOrganization);
        assertEquals(organizationName, savedOrganization.getOrganizationName());
        assertEquals(eventsHeld, savedOrganization.getEventsHeldList());
    }

    @Test
    void updateOrganization_ExistingOrganization_Successful() {
        // GIVEN
        String organizationName = "ExistingOrganization";
        List<String> eventsHeld = Arrays.asList("Event1", "Event2");
        Organization organization = new Organization(organizationName, eventsHeld);

        when(organizationRepository.existsById(organizationName)).thenReturn(true);
        when(organizationRepository.save(any(OrganizationRecord.class))).thenReturn(mockOrganization);

        // WHEN
        organizationService.updateOrganization(organization);

        // THEN
        verify(organizationRepository).save(any(OrganizationRecord.class));
    }


    @Test
    void updateOrganization_NonExistingOrganization_DoesNothing() {
        // GIVEN
        when(organizationRepository.existsById(any())).thenReturn(false);


        // WHEN
        organizationService.updateOrganization(new Organization("name", new ArrayList<>()));

        // THEN
        verify(organizationRepository, never()).save(any(OrganizationRecord.class));
    }

    @Test
    void getOrganizationByName_NonExistingOrganization_ReturnsNull() {
        // GIVEN
        String organizationName = "NonExistingOrganization";
        when(organizationRepository.findById(organizationName)).thenReturn(Optional.empty());

        // WHEN
        Organization result = organizationService.getOrganizationByName(organizationName);

        // THEN
        assertNull(result);
    }
    @Test
    void deleteOrganizationByName_ExistingOrganization_Successful() {
        // GIVEN
        String organizationName = "ExistingOrganization";

        // WHEN
        organizationService.deleteOrganizationByName(organizationName);

        // THEN
        verify(organizationRepository).deleteById(organizationName);
    }
    @Test
    void getOrganizationByName_ExistingOrganization_Successful() {
        // GIVEN
        String organizationName = "TestOrganization";
        List<String> eventsHeld = Arrays.asList("Event1", "Event2");
        OrganizationRecord organizationRecord = new OrganizationRecord(organizationName, eventsHeld);

        when(organizationRepository.findById(organizationName)).thenReturn(Optional.of(organizationRecord));

        // WHEN
        Organization result = organizationService.getOrganizationByName(organizationName);

        // THEN
        assertNotNull(result);
        assertEquals(organizationName, result.getOrganizationName());
        assertEquals(eventsHeld, result.getOrganizationEventCount());
    }

    @Test
    void processExpiredEvents_AllEventsExpired_Successful() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        List<EventRecord> eventRecords = Arrays.asList(
                new EventRecord("Event1", "Event1", "Location1", now.minusDays(2).toString(), now.minusDays(1).toString(), new ArrayList<>(), new ArrayList<>(), "Organization1"),
                new EventRecord("Event2", "Event2", "Location2", now.minusDays(2).toString(), now.minusDays(1).toString(), new ArrayList<>(), new ArrayList<>(), "Organization2")
        );
        when(eventRepository.findAll()).thenReturn(eventRecords);
        when(organizationRepository.save(any())).thenReturn(null);

        // WHEN
        organizationService.processExpiredEvents();

        // THEN
        verify(eventRepository).deleteAll(any());
    }





}
