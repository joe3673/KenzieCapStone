import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";


/*
 UserProfile. Handles javascript for the
 user profile page.

 This page should not need to connect to the api
 as all the user information should be stored
 in the web browser cache.
 */

class UserProfile extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([], this);
        this.dataStore = new DataStore();
    }


    async mount() {
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const userProfile = new UserProfile();
    userProfile.mount();
};

window.addEventListener('DOMContentLoaded', main);
