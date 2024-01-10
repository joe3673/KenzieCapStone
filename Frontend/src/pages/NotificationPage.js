import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import NotificationClient from "../api/NotificationClient";

/*
 AccountSignIn. Handles javascript for the
 account sign in page.
 */

class NotificationPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['sendData'], this);
        this.dataStore = new DataStore();
    }


    async mount() {
        this.client = new NotificationClient();
        document.getElementById('create').addEventListener('submit', this.sendData);
        document.getElementById('get').onclick = this.getData;
    }
    async sendData(event){
        event.preventDefault()
        var stored = localStorage['unitybuildersuserinfo'];
        let user = 0;
        if(stored){
            user = JSON.parse(stored);
        }
        let data = document.getElementById("data").value;
        let test = await this.client.sendNotification(user.username, data, this.errorHandler);
        console.log(test)
    }

    async getData(event){
        event.preventDefault()
        let id = document.getElementById("id").value;
        console.log(id)
        let client2 = new NotificationClient();
        var stored = localStorage['unitybuildersuserinfo'];
        let user = 0;
        if(stored){
            user = JSON.parse(stored);
        }
        let data = await client2.getNotification(user.username, id, this.errorHandler);
        console.log(data)
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const notificationPage = new NotificationPage();
    notificationPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
