import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/UserClient";

/*
 AccountCreation. Handles javascript for the
 account creation page.

 Creating a user
 */

class AccountCreation extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['submitUser'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        this.client = new UserClient();
        document.getElementById("submit").onclick = this.submitUser
    }

    //Function for user submission
    async submitUser() {
        let firstName = document.getElementById('create-user-firstName').value;
        let lastName = document.getElementById('create-user-lastName').value;
        let userName = document.getElementById('create-user-userName').value;
        let email = document.getElementById('create-user-email').value;
        let password = document.getElementById('create-user-password').value;
        let userType = document.getElementById('create-user-userType').value;
        const user = await this.client.addUser(userName, password, email, firstName, lastName, userType,  this.errorHandler);

        if (user) {
            this.showMessage('Created user')
            window.location.href = "index.html";
        }
        else {
            this.errorHandler("Error creating!  Try again...");
        }
    }

}


//Main method to run when the page contents have loaded
const main = async () => {
    const accountCreation = new AccountCreation();
    accountCreation.mount();
};

window.addEventListener('DOMContentLoaded', main);
