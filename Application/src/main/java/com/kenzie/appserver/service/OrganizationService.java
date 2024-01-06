package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.OrganizationRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.OrganizationRecord;
import com.kenzie.appserver.service.model.Organization;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final EventRepository eventRepository;



    public OrganizationService(OrganizationRepository organizationRepository, EventRepository eventRepository) {
        this.organizationRepository = organizationRepository;
        this.eventRepository = eventRepository;
    }

    @Scheduled(fixedRate = 3600000)
    public void processExpiredEvents(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        Iterable<EventRecord> allEvents = eventRepository.findAll();

        Map<String, Organization> organizationsToUpdate = new HashMap<>();

        for (EventRecord record : allEvents) {
            if (LocalDateTime.parse(record.getEndTime()).plusHours(24).isAfter(currentDateTime)) {
                Organization organization = organizationsToUpdate
                        .computeIfAbsent(record.getEventSponsor(), k -> getOrganizationByName(k));

                organization.getOrganizationEventCount().add(record.getName());
            }
        }

        for (Organization organization : organizationsToUpdate.values()) {
            updateOrganization(organization);
        }

        List<EventRecord> recordsToDelete = StreamSupport.stream(allEvents.spliterator(), false)
                .filter(record -> LocalDateTime.parse(record.getEndTime()).plusHours(24).isAfter(currentDateTime))
                .collect(Collectors.toList());

        eventRepository.deleteAll(recordsToDelete);
    }


    public OrganizationRecord addNewOrganization(String name, List<String> eventsHeld){

        OrganizationRecord organization = new OrganizationRecord(name,eventsHeld);

        organizationRepository.save(organization);

        return organization;
    }

    public void updateOrganization(Organization organization){
        if(organizationRepository.existsById(organization.getOrganizationName())){
            OrganizationRecord organizationRecord = new OrganizationRecord(
                    organization.getOrganizationName(),
                    organization.getOrganizationEventCount());

            organizationRepository.save(organizationRecord);
        }
    }

    public void deleteOrganizationByName(String name){
        organizationRepository.deleteById(name);

    }

    public Organization getOrganizationByName(String name){

        Optional<OrganizationRecord> record = organizationRepository.findById(name);

        if(record.isEmpty()){
            return null;
        }
        Organization organization = new Organization(record.get().getOrganizationName(),record.get().getEventsHeldList());

        return  organization;



    }


}
