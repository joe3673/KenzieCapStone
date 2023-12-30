package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.EventCreateRequest;
import com.kenzie.appserver.controller.model.EventResponse;
import com.kenzie.appserver.controller.model.EventUpdateRequest;
import com.kenzie.appserver.service.EventService;
import com.kenzie.appserver.service.model.Event;
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
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService) {
        this.eventService = eventService;
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

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<Event> events = eventService.findAllEvents();

        if (events == null || events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EventResponse> responses = new ArrayList<>();
        for (Event event : events) {
            responses.add(createEventResponse(event));
        }
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<EventResponse> addNewEvent(@RequestBody EventCreateRequest eventCreateRequest) {
        // Should I put Input validation

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
    public ResponseEntity<Void> deleteEventById(@PathVariable("eventId") String eventId) {
        Event eventToDelete = eventService.findByEventId(eventId);

        if (eventToDelete == null) {
            return ResponseEntity.notFound().build();
        }

        if (hasEventOccurred(eventToDelete.getEndTime())) {
            eventService.deleteEvent(eventId);
            return ResponseEntity.noContent().build();
        } else {
//            return Status Code or log message if event not found or not occurred?
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    private boolean hasEventOccurred(LocalDateTime eventEndTime) {
        return LocalDateTime.now().isAfter(eventEndTime);
    }
//  Not sure I need this in the Event Controller
    @GetMapping("/{userId}")
    public ResponseEntity<List<EventResponse>> getEventsAttendedByFriends(@PathVariable("userId") String userId) {
        List<Event> eventsAttendedByFriends = eventService.getEventsAttendedByFriends(userId);

        if (eventsAttendedByFriends == null || eventsAttendedByFriends.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EventResponse> responses = new ArrayList<>();
        for (Event event : eventsAttendedByFriends) {
            responses.add(createEventResponse(event));
        }
        return ResponseEntity.ok(responses);
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
