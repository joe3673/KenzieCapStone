import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/EventClient";

/*
 EventPageVisitor. Handles javascript for the
 Event Visitor page.
 */

class EventPageVisitor extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['loadEvents', 'displayEvents'], this);
        this.dataStore = new DataStore();
    }


    async mount() {
        this.client = new EventClient();
        await this.loadEvents();

    }

     async loadEvents() {
         // get all events,sponsored events, &attended events from backend
         const allEvents = await this.client.getAllEvents(this.errorHandler);

         //Display events in the corresponding boxes
         this.displayEvents('all-events-box', allEvents);

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
    const eventPageVisitor = new EventPageVisitor();
    eventPageVisitor.mount();
};

window.addEventListener('DOMContentLoaded', main);
