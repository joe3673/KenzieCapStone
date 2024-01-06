import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/EventClient";

/*
 EventPageUser. Handles javascript for the
 Event User page.

 ***I was trying something here but I will probably go back to my previous version, please ignore for now.
 */

class EventPageUser extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        this.client = new EventClient();
        this.loadEvents();
    }

    async loadEvents() {
        // get all events,sponsored events, &attended events from backend
        const allEvents = await this.client.getAllEvents();
        const sponsoredEvents = await this.client.getSponsoredEvents();
        const attendedEvents = await this.client.getAttendedEvents();

        //Display events in the corresponding boxes
        this.displayEvents('all-events-list', allEvents);
        this.displayEvents('sponsored-events-list', sponsoredEvents);
        this.displayEvents('attended-events-list', attendedEvents);
    }

    displayEvents(containerId, events) {
        //events in a scrolling list inside th container
        const container = document.getElementById(containerId);
        if (container) {
            const eventList = container.querySelector('ul');
            if (eventList) {
                events.forEach(event => {
                    const listItem = document.createElement('li');
                    listItem.textContent = event.name;
                    eventList.appendChild(listItem);
                });
            }
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const eventPageUser = new EventPageUser();
    eventPageUser.mount();
};

window.addEventListener('DOMContentLoaded', main);
