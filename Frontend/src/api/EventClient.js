import BaseClass from "../util/baseClass";
import axios from 'axios'

/*
Event client. It handles all interactions with
the event controller in the backend.
*/

export default class EventClient extends BaseClass {

    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady();
        }
    }

    async getAllEvents() {
        try {
            const response = await this.client.get('/Event');
            return response.data;
        } catch (error) {
            this.handleError("getAllEvents", error);
        }
    }

    //I was just trying something here, ignore for now
    async getSponsoredEvents() {

        try {
            const response = await this.client.get('/Event/sponsored');
            return response.data;
        } catch (error) {
            this.handleError("getSponsoredEvents", error);
        }
    }

    //I was just trying something here, ignore for now
    async getAttendedEvents() {
        try {
            const response = await this.client.get('/Event/attended');
            return response.data;
        } catch (error) {
            this.handleError("getAttendedEvents", error);
        }
    }

    async deleteEvent(eventId, errorCallback) {
        try {
            const response = await this.client.delete(`/Event/${eventId}`);
            return response.data;
        } catch (error) {
            this.handleError("deleteEvent", error, errorCallback);
        }
    }


}