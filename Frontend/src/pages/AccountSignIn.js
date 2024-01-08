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
        this.bindClassMethods(['signIn'], this);
        this.dataStore = new DataStore();
    }


    async mount() {
        this.client = new UserClient();
        document.getElementById("submit").onclick = this.signIn
    }

    async signIn(){
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;


        let user = await this.client.loginUser(username, password, this.errorHandler);

        localStorage['unitybuildersuserinfo'] = JSON.stringify(user);
        if (user) {
            this.showMessage("logged in");
            window.location.href = "index.html";
        }
        else {
            this.errorHandler("error.");
        }
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
