package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.EventCreateRequest;
import com.kenzie.appserver.controller.model.EventResponse;
import com.kenzie.appserver.controller.model.EventUpdateRequest;
import com.kenzie.appserver.service.EventService;
import com.kenzie.appserver.service.model.Event;
import com.kenzie.capstone.service.model.NotificationData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/Event")
public class EventController{
    private EventService eventService;
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}/notification")
    public NotificationData getEventNotification(@PathVariable String eventId) {
        return eventService.getNotificationDataFromLambda(eventId);
    }
    @PostMapping("/notification")
    public NotificationData setEventNotification(@RequestBody String data) {
        return eventService.setNotificationDataFromLambda(data);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable("eventId") String eventId) {
        Event event = eventService.findByEventId(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        EventResponse eventResponse = createEventResponse(event);
        return ResponseEntity.ok(eventResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<Event> events = eventService.findAllEvents();
        List<EventResponse> responses = new ArrayList<>();
        for (Event event : events) {
            responses.add(createEventResponse(event));
        }
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<EventResponse> addNewEvent(@RequestBody EventCreateRequest eventCreateRequest) {
        Event event = new Event(UUID.randomUUID().toString(),
                eventCreateRequest.getName(),
                eventCreateRequest.getLocation(),
                eventCreateRequest.getStartTime(),
                eventCreateRequest.getEndTime(),
                eventCreateRequest.getEventSponsor());
        eventService.addNewEvent(event);

        EventResponse eventResponse = createEventResponse(event);
        return ResponseEntity.created(URI.create("//" + eventResponse.getEventId())).body(eventResponse);
    }
/*
    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable("eventId") String eventId, @RequestBody EventUpdateRequest eventUpdateRequest) {
        // Should I put Input validation

        Event event = new Event(eventId,
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

 */

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEventById(@PathVariable("eventId") String eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }



    private EventResponse createEventResponse(Event event) {
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
