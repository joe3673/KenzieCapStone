
/*
 UserProfile. Handles javascript for the
 user profile page.

 This page should not need to connect to the api
 as all the user information should be stored
 in the web browser cache.
 */

import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";

class UserProfile extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods([], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        var stored = localStorage['unitybuildersuserinfo'];
        let user = 0
        if (stored) user = JSON.parse(stored);
        document.getElementById("firstName").textContent=user.firstName;
        document.getElementById("lastName").textContent=user.lastName;
        document.getElementById("userName").textContent=user.username;
        document.getElementById("email").textContent=user.email;
        document.getElementById("password").textContent=user.password;
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
