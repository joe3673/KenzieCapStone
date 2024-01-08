
/*
 EventPageOrganizer. Handles javascript for the
 Event Organizer page.
 */

import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/EventClient";

class EventPageOrganizer extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['deleteEvent', 'onCreate'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById('create-form').addEventListener('submit', this.onCreate);
        this.client = new EventClient();
        document.getElementById('delete').onclick = this.deleteEvent();
    }


    async deleteEvent() {
            let eventId = document.getElementById("d").value;
            await this.client.deleteEvent(eventId, this.errorHandler);
            this.showMessage('Deleted event with ID: ${eventId}');
    }



    async onCreate(event) {
        event.preventDefault();
        this.dataStore.set("Event", null);
        let name = document.getElementById("create-event-name").value;
        let location = document.getElementById("create-event-location").value;
        let startTime = document.getElementById("create-event-startTime").value;
        let endTime = document.getElementById("create-event-endTime").value;
        let eventSponsor = document.getElementById("create-event-sponsor").value;
        const currentEvent = await this.client.addEvent(name, location, startTime, endTime, eventSponsor, this.errorHandler);
        this.dataStore.set("Event", currentEvent);
        if (currentEvent) {
            this.showMessage('Created Event')
        }
        else {
            this.errorHandler("Error creating!  Try again...");
        }
    }

}

const main = async () => {
    const eventPageOrganizer = new EventPageOrganizer();
    eventPageOrganizer.mount();
};

window.addEventListener('DOMContentLoaded', main);
