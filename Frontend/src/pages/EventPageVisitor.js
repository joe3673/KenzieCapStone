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
    const eventPageVisitor = new EventPageVisitor();
    eventPageVisitor.mount();
};

window.addEventListener('DOMContentLoaded', main);
