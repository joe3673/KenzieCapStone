import BaseClass from "../util/baseClass";
import axios from 'axios'

/*
 User Client. It handles all interactions with
 the user controller in the backend.
 */
export default class NotificationClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getNotification', 'sendNotification'];
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
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    //create user - post req
    async sendNotification(userId, data, errorCallback) {
        try {
            const response = await this.client.post(`/User/${userId}/notification`, {
                "data": data
            });
            return response.data;
        } catch (error) {
            this.handleError("sendNotification", error, errorCallback);
        }
    }





//get user - sends get req
    async getNotification(userId, id, errorCallback){
        try {
            const response = await this.client.put(`/User/${userId}/notification`, {
                "id": id
            });
            return response.data;
        } catch (error) {
            this.handleError("getNotification", error, errorCallback)
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