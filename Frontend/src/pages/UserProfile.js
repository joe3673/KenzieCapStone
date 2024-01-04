import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";


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
        const editButton = document.getElementById('edit-profile-btn');
        const saveButton = document.getElementById('save-changes-btn');

        editButton.addEventListener('click', this.editProfile);
        saveButton.addEventListener('click', this.saveChanges);
    }

    editProfile() {
        document.querySelectorAll('.user-info input, .user-info select').forEach(field => {
            field.removeAttribute('disabled');
        });
        document.getElementById('save-changes-btn').style.display = 'block';
    }

    updateUser() {
        document.querySelectorAll('.user-info input, .user-info select').forEach(field => {
            field.setAttribute('disabled', 'disabled');
        });

        document.getElementById('save-changes-btn').style.display = 'none';
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
