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
    }

    //Function for user submission
    submitUser() {
        const firstName = document.getElementById('create-user-firstName').value;
        const lastName = document.getElementById('create-user-lastName').value;
        const userName = document.getElementById('create-user-userName').value;
        const email = document.getElementById('create-user-email').value;
        const password = document.getElementById('create-user-password').value;
        const userType = document.getElementById('create-user-userType').value;

        if (firstName && lastName && userName && email && password && userType) {
                    //submituser data to the server using UserClient
                    this.client.createUser({
                        firstName,
                        lastName,
                        userName,
                        email,
                        password,
                        userType,
                    });
                } else {
                    console.error('All fields are required');

                }
            }
        }



        // **Probably need to update this later - Submit the user data to the server using UserClient
        this.client.createUser({
            firstName,
            lastName,
            userName,
            email,
            password,
            userType,
        });
    }
}

//Main method to run when the page contents have loaded
const main = async () => {
    const accountCreation = new AccountCreation();
    accountCreation.mount();
};

window.addEventListener('DOMContentLoaded', main);
