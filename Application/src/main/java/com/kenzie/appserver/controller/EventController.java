package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.EventCreateRequest;
import com.kenzie.appserver.controller.model.EventResponse;
import com.kenzie.appserver.controller.model.EventUpdateRequest;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.service.EventService;
import com.kenzie.appserver.service.model.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/events")
public class EventController {

    private EventService eventService;

    EventController (EventService eventService){
        this.eventService = eventService;
    }


    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> searchByEventId(@PathVariable("eventId")String eventId){
        Event event = eventService.findByEventId(eventId);
        if(event == null){
            return ResponseEntity.notFound().build();
        }
        EventResponse eventResponse = createEventResponse(event);
        return ResponseEntity.ok(eventResponse);

    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents(){
        List<Event> events = eventService.findAllEvents();

        if(events == null || events.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<EventResponse> responses = new ArrayList<>();
        for(Event event : events){
            responses.add(this.createEventResponse(event));
        }
        return ResponseEntity.ok(responses);
    }
    @PostMapping
    public ResponseEntity<EventResponse> addNewEvent(@RequestBody EventCreateRequest eventCreateRequest){
        Event event = new Event(UUID.randomUUID().toString(),
                eventCreateRequest.getName(),
                eventCreateRequest.getLocation(),
                eventCreateRequest.getStartTime(),
                eventCreateRequest.getEndTime(),
                eventCreateRequest.getPeopleAttending(),
                eventCreateRequest.getPeopleAttended(),
                eventCreateRequest.getEventSponsor());
        eventService.addNewEvent(event);

        EventResponse eventResponse = createEventResponse(event);
        return ResponseEntity.created(URI.create("/events" + eventResponse.getEventId())).body(eventResponse);

    }
    @PutMapping
    public ResponseEntity<EventResponse> updateEvent(@RequestBody EventUpdateRequest eventUpdateRequest){
        Event event = new Event(eventUpdateRequest.getEventId(),
                eventUpdateRequest.getName(),
                eventUpdateRequest.getLocation(),
                eventUpdateRequest.getStartTime(),
                eventUpdateRequest.getEndTime(),
                eventUpdateRequest.getPeopleAttending(),
                eventUpdateRequest.getPeopleAttended(),
                eventUpdateRequest.getEventSponsor());
        eventService.updateEvent(event);

        EventResponse eventResponse = createEventResponse(event);

        return ResponseEntity.ok(eventResponse);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity deleteEventById(@PathVariable("eventId")String eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }


    private EventResponse createEventResponse(Event event){
        EventResponse eventResponse = new EventResponse();
                eventResponse.setEventId(event.getEventID());
                eventResponse.setName(event.getName());
                eventResponse.setLocation(event.getLocation());
                eventResponse.setStartTime(event.getStartTime());
                eventResponse.setEndTime(event.getEndTime());
                eventResponse.setPeopleAttending(event.getPeopleAttending());
                eventResponse.setPeopleAttended(event.getPeopleAttended());
                eventResponse.setEventSponsor(event.getEventSponsor());
        return eventResponse;
    }


}
