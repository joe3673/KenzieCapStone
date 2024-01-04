import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/EventClient";

/*
 EventPageOrganizer. Handles javascript for the
 Event Organizer page.
 */

import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/EventClient";

class EventCreationPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'renderEvent'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById('create-form').addEventListener('submit', this.onCreate);
        this.client = new EventClient();

        this.dataStore.addChangeListener(this.renderEvent);
    }

    async deleteEvent(eventId) {
        try {
            await this.client.deleteEvent(eventId, this.errorHandler);
            this.showMessage(`Deleted event with ID: ${eventId}`);
            await this.renderEvents();
        } catch (error) {
            this.errorHandler("Error deleting event: " + error.message);
        }
    }


    async renderEvent() {
        let resultArea = document.getElementById("result-info");

        const event = this.dataStore.get("event");

        if (event) {
            resultArea.innerHTML =
               ` <div>Event ID: ${event.id}</div>
                <div>Name: ${event.name}</div>
                <div>Start Time: ${event.startTime}</div>
                <div>End Time: ${event.endTime}</div>
                <div>Location: ${event.eventLocation}</div>
                <div>Sponsor: ${event.eventSponsor}</div>
                <div>Status: ${event.status}</div>`;
        } else {
            resultArea.innerHTML = '<div>No event available</div>';
        }
    }

    async onCreate(event) {
        event.preventDefault();

        const eventId = document.getElementById("create-event-id").value;
        const name = document.getElementById("create-event-name").value;
        const startTime = document.getElementById("create-event-startTime").value;
        const endTime = document.getElementById("create-event-endTime").value;
        const eventLocation = document.getElementById("create-event-location").value;
        const eventSponsor = document.getElementById("create-event-sponsor").value;
        const status = document.getElementById("create-event-status").value;

        const eventObject = {
            id: eventId,
            name: name,
            startTime: startTime,
            endTime: endTime,
            eventLocation: eventLocation,
            eventSponsor: eventSponsor,
            status: status,
            peopleAttending: [],
            peopleAttended: []
        };

        try {
            const createdEvent = await this.client.createEvent(eventObject, this.errorHandler);
            this.dataStore.set("event", createdEvent);

            if (createdEvent) {
                this.showMessage(`Created ${createdEvent.name}!`);
            } else {
                this.errorHandler("Error creating! Try again...");
            }
        } catch (error) {
            this.errorHandler("Error creating event: " + error.message);
        }
    }
}

const main = async () => {
    const eventCreationPage = new EventCreationPage();
    eventCreationPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
