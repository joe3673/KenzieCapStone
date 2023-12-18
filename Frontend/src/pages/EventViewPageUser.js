import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/EventClient";

/*
 EventPageUser. Handles javascript for the
 Event User page.
 */

class EventPageUser extends BaseClass {

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
    const eventPageUser = new EventPageUser();
    eventPageUser.mount();
};

window.addEventListener('DOMContentLoaded', main);
