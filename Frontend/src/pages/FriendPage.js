import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/UserClient";

/*
 FriendPage. Handles javascript for the
 friend page.
 */

class FriendPage extends BaseClass {

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
    const friendPage = new FriendPage();
    friendPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
