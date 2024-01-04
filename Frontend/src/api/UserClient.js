import BaseClass from "../util/baseClass";
import axios from 'axios'

/*
 User Client. It handles all interactions with
 the user controller in the backend.
 */
export default class UserClient extends BaseClass {

    constructor(props = {}){
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
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    //create user - post req
    async createUser(user, errorCallback) {
        try {
            const response = await this.client.post('/User', user);
            return response.data;
        } catch (error) {
            this.handleError("createUser", error, errorCallback);
        }
    }

    //login - sends post req, might need to update?
    async loginUser(username, password, errorCallback) {
        try {
            const response = await this.client.post('/User/login', { username, password });
            return response.data;
        } catch (error) {
            this.handleError("loginUser", error, errorCallback);
        }
    }

    //to edit their profile - sends a put req
    async updateUser(id, updatedUser, errorCallback) {
        try {
            const response = await this.client.put(`/User/${id}`, updatedUser);
            return response.data;
        } catch (error) {
            this.handleError("updateUser", error, errorCallback);
        }
    }



//get user - sends get req
  async getUser(id, errorCallback) {
            try {
                const response = await this.client.get(`/User/${id}`);
                return response.data;
            } catch (error) {
                this.handleError("getUser", error, errorCallback)
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