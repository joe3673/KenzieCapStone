import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UserClient from "../api/UserClient";


/*
 FriendPage. Handles javascript for the
 friend page.

 ***I'm going to be working on this next but need to push code, please check with me before assisting.***
 */

class FriendPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['addFriend'], this);
        this.dataStore = new DataStore();
    }


    async mount() {
         this.client = new UserClient();
         document.getElementById('add').onclick = this.addFriend;
         document.getElementById('add-friend-form').addEventListener('submit', this.addFriend);
         document.getElementById('get-friend-events').addEventListener('submit', this.getFriendEvents);
         let stored = localStorage['unitybuildersuserinfo'];
         let user = 0;
         if (stored) user = JSON.parse(stored);
         let resultArea = document.getElementById("friends-list");
  		 let htmlResponse = "<ul>";
  		 let friendList = user.friends;
	     for(let i = 0; i < friendList.length; ++i){
		    let friend = friendList[i];
		    htmlResponse += `<li><h3>username ${friend}</h3></li>`;
			}
         await this.getAllUsers()
  		 resultArea.innerHTML = htmlResponse;

    }

    async addFriend(){
        let stored = localStorage['unitybuildersuserinfo'];
        let user = 0;
        if (stored) user = JSON.parse(stored);
        let frienduserName = document.getElementById("friend-username").value;
        await this.client.addFriend(user.username, frienduserName, this.errorHandler);
        user = await this.client.getUser(user.username);
        localStorage['unitybuildersuserinfo'] = JSON.stringify(user);
         let resultArea = document.getElementById("friends-list");
  		 let htmlResponse = "<ul>";
  		 let friendList = user.friends;
	     for(let i = 0; i < friendList.length; ++i){
		 let friend = friendList[i];
		 htmlResponse += `<li><h3>username ${friend}</h3></li>`;

			}
  		 resultArea.innerHTML = htmlResponse;
    }

    async getFriendEvents(event){
        event.preventDefault()
         let frienduserName = document.getElementById("username").value;
         let client = new UserClient()
        console.log(frienduserName)
         let events = await client.getEventsAttendedByFriends(frienduserName, this.errorHandler);
         console.log(events)
         let resultArea = document.getElementById("friend-events");
  		 let htmlResponse = "<ul>";
	     for(let i = 0; i < events.length; ++i){
		    let event = events[i];
		    htmlResponse += `<li><h3>ID ${event.eventId}</h3><h3>Name ${event.name}</li>`;

         }
  		 resultArea.innerHTML = htmlResponse;
    }
    async getAllUsers() {
            const users = await this.client.getAllUsers(this.errorHandler);
            let userListHtml = "<ul>";
            for (const user of users) {
                userListHtml += `<li><h3>Username: ${user.username}</h3></li>`;
            }
            userListHtml += "</ul>";

            const userListArea = document.getElementById("user-list");
            userListArea.innerHTML = userListHtml;
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
