import BaseClass from "../util/baseClass";
import axios from 'axios'

/*
 User Client. It handles all interactions with
 the user controller in the backend.
 */
export default class UserClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'addUser', 'loginUser', 'getUser', 'getAllUsers', 'deleteUser', 'joinEvent', 'addFriend', 'shareEventsWithFriends', 'getEventsAttendedByFriends'];
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
    async addUser(userName, password, email, firstName, lastName, userType, errorCallback) {
        try {
            const response = await this.client.post(`/User`, {
                "username": userName,
                "password": password,
                "email": email,
                "firstName": firstName,
                "lastName": lastName,
                "userType": userType
            });
            return response.data;
        } catch (error) {
            this.handleError("addUser", error, errorCallback);
        }
    }

    //login - sends post req, might need to update?
    async loginUser(userName, password, errorCallback) {
        try {
            const response = await this.client.post(`/User/${userName}`, {
             "userName": userName,
             "password": password
             });
            return response.data;
        } catch (error) {
            this.handleError("loginUser", error, errorCallback);
        }
    }





//get user - sends get req
    async getUser(userId, errorCallback){
        try {
            const response = await this.client.get(`/User/${userId}`);
            return response.data;
        } catch (error) {
                this.handleError("getUser", error, errorCallback)
            }
    }

    async getAllUsers(errorCallback){
        try {
            const response = await this.client.get(`/User/all`);
            return response.data;
        } catch (error) {
                 this.handleError("getAllUsers", error, errorCallback)
            }
    }

    async deleteUser(userId, errorCallback){
        try {
            const response = await this.client.delete(`/User/${userId}`);
            return response.data;
        } catch (error) {
                this.handleError("deleteUser", error, errorCallback)
            }
    }

    async joinEvent(userId, eventId, errorCallback){
         try {
             const response = await this.client.post(`/User/${userId}/EventList`, {
             "userId": userId,
             "eventId": eventId
             });
             return response.data;
         } catch (error) {
                 this.handleError("joinEvent", error, errorCallback)
            }
    }

    async addFriend(userId, friendId, errorCallback){
         try {
             const response = await this.client.post(`/User/${userId}/FriendList`, {
             "userId": userId,
             "friendId": friendId
             });
             return response.data;
         } catch (error) {
                 this.handleError("addFriend", error, errorCallback)
            }
    }

    async shareEventsWithFriends(userId, eventId, errorCallback){
         try {
             const response = await this.client.put(`/User/${userId}`, {
             "userId": userId,
             "eventId": eventId
             });
             return response.data;
         } catch (error) {
                 this.handleError("shareEventsWithFriends", error, errorCallback)
            }
    }

    async getEventsAttendedByFriends(userId, errorCallback){
         try {
             const response = await this.client.get(`/User/${userId}/FriendList/Events`);
             return response.data;
         } catch (error) {
                 this.handleError("shareEventsWithFriends", error, errorCallback)
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