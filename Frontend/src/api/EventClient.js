import BaseClass from "../util/baseClass";
import axios from 'axios'

/*
Event client. It handles all interactions with
the event controller in the backend.
*/

export default class EventClient extends BaseClass {

    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'getAllEvents', 'getEvent', 'addEvent', 'deleteEvent'];
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

    async getAllEvents(errorCallback) {
        try {
            const response = await this.client.get(`/Event/all`);
            return response.data;
        } catch (error) {
            this.handleError("getAllEvents", error, errorCallback);
        }
    }

    async getEvent(eventId, errorCallback) {
        try {
            const response = await this.client.get(`/Event/${eventId}`);
            return response.data;
        } catch (error) {
            this.handleError("getAllEvents", error, errorCallback);
        }
    }

    async addEvent(name, location, startTime, endTime, eventSponsor, errorCallback) {
        try {
            const response = await this.client.post(`/Event`, {
                "name": name,
                "location": location,
                "startTime": startTime,
                "endTime": endTime,
                "eventSponsor": eventSponsor
            });
            return response.data;
        } catch (error) {
            this.handleError("getAllEvents", error, errorCallback);
        }
    }


    async deleteEvent(eventId, errorCallback) {
        try {
            await this.client.delete(`/Event/${eventId}`);
        } catch (error) {
            this.handleError("deleteEvent", error, errorCallback);
        }
    }


    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }


}