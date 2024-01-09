package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.OrganizationRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.OrganizationRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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




}
