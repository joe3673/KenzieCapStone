package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.EventRecord;


import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EventService {
    private EventRepository eventRepository;

    private UserRepository userRepository;

    @Autowired
    private CacheDAO cacheDAO;


    public EventService(EventRepository eventRepository, UserRepository userRepository, CacheDAO cacheDAO){
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.cacheDAO = cacheDAO;
    }





        public List<Event> findAllEvents() {
        List<Event> events = new ArrayList<>();

        Iterable<EventRecord> eventRecordIterable = eventRepository.findAll();
        for (EventRecord record : eventRecordIterable) {
            events.add(new Event(
                    record.getEventID(),
                    record.getName(),
                    record.getLocation(),
                    record.getStartTime(),
                    record.getEndTime(),
                    record.getPeopleAttending(),
                    record.getPeopleAttended(),
                    record.getEventSponsor()));
        }
        return events;
    }

    public Event findByEventId(String eventId){
        EventRecord eventRecord = cacheDAO.getRecord(eventId);
        if(eventRecord == null){
            Optional<EventRecord> record = eventRepository.findById(eventId);
            if(record.isPresent()){
                eventRecord = record.get();
                cacheDAO.addRecord(eventRecord);
                return new Event(eventRecord.getEventID(), eventRecord.getName(), eventRecord.getLocation(), eventRecord.getStartTime(), eventRecord.getEndTime(), eventRecord.getPeopleAttending(), eventRecord.getPeopleAttended(), eventRecord.getEventSponsor());
            }
            return null;

        }
        return new Event(eventRecord.getEventID(), eventRecord.getName(), eventRecord.getLocation(), eventRecord.getStartTime(), eventRecord.getEndTime(), eventRecord.getPeopleAttending(), eventRecord.getPeopleAttended(), eventRecord.getEventSponsor());

    }

    public Event addNewEvent(Event event) {
        EventRecord eventRecord = new EventRecord(
                event.getEventID(),
                event.getName(),
                event.getLocation(),
                event.getStartTime(),
                event.getEndTime(),
                event.getPeopleAttending(),
                event.getPeopleAttended(),
                event.getEventSponsor());
        eventRepository.save(eventRecord);
        return event;
    }

    /*
    public void updateEvent(Event event) {
        if (eventRepository.existsById(event.getEventID())){
            EventRecord eventRecord = new EventRecord(
                    event.getEventID(),
                    event.getName(),
                    event.getLocation(),
                    event.getStartTime(),
                    event.getEndTime(),
                    event.getPeopleAttending(),
                    event.getPeopleAttended(),
                    event.getEventSponsor());
            eventRepository.save(eventRecord);
        }
    }
     */

    public void deleteEvent(String eventId) {
        Event event = findByEventId(eventId);
        if(event != null){
            List<String> peopleAttending = event.getPeopleAttending();
            for(int i = 0, size = peopleAttending.size(); i < size; ++i){
                Optional<UserRecord> ur = userRepository.findById(peopleAttending.get(i));
                if(ur.isPresent()){
                    UserRecord userRecord = ur.get();
                    userRecord.getEventsList().remove(eventId);
                    userRepository.save(userRecord);
                }
            }
            cacheDAO.deleteRecord(eventId);
            eventRepository.deleteById(eventId);
        }
    }
    private boolean hasEventOccurred(LocalDateTime eventEndTime) {
        return LocalDateTime.now().isAfter(eventEndTime);
    }

}
