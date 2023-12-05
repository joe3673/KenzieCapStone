import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/EventClient";

/*
 EventPageOrganizer. Handles javascript for the
 Event Organizer page.
 */

class EventPageOrganizer extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([], this);
        this.dataStore = new DataStore();
    }


    async mount() {
        this.client = new EventClient();
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const eventPageOrganizer = new EventPageOrganizer();
    eventPageOrganizer.mount();
};

window.addEventListener('DOMContentLoaded', main);
