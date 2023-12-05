import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/UserClient";

/*
 AccountSignIn. Handles javascript for the
 account sign in page.
 */

class AccountSignIn extends BaseClass {

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
    const accountSignIn = new AccountSignIn();
    accountSignIn.mount();
};

window.addEventListener('DOMContentLoaded', main);
