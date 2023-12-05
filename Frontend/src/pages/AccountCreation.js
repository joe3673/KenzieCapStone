import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/UserClient";

/*
 AccountCreation. Handles javascript for the
 account creation page.
 */

class AccountCreation extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([], this);
        this.dataStore = new DataStore();
    }


    async mount() {
        this.client = new UserClient();
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const accountCreation = new AccountCreation();
    accountCreation.mount();
};

window.addEventListener('DOMContentLoaded', main);
