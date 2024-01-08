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
         document.getElementById('add-friend-form').addEventListener('submit', this.addFriend);
         document.getElementById('get-friend-events').addEventListener('submit', this.GetFriendEvents);
         var stored = localStorage['unitybuildersuserinfo'];
         let user = 0;
         if (stored) user = JSON.parse(stored);
         let resultArea = document.getElementById("friends-list");
  		 let htmlResponse = "<ul>";
  		 let friendList = user.friends;
	     for(let i = 0; i < friendList.length; ++i){
		 let friend = friendList[i];
		 htmlResponse += `<li><h3>username ${friend.userName}</h3></li>`;

			}
  		 resultArea.innerHTML = htmlResponse;

    }

    async addFriend(){
        let frienduserName = document.getElementById("friend-username");
        await this.client.addFriend(user.userName, frienduserName, this.errorHandler);
        let user = await this.client.getUser(user.userName);
        localStorage['unitybuildersuserinfo'] = JSON.stringify(user);
         let resultArea = document.getElementById("friends-list");
  		 let htmlResponse = "<ul>";
  		 let friendList = user.friends;
	     for(let i = 0; i < friendList.length; ++i){
		 let friend = friendList[i];
		 htmlResponse += `<li><h3>username ${friend.userName}</h3></li>`;

			}
  		 resultArea.innerHTML = htmlResponse;
    }

    async getFriendEvents(){
         let frienduserName = document.getElementById("username");
         let events = await this.client.getEventsAttendedByFriends(frienduserName, this.errorHandler);
         let resultArea = document.getElementById("friend-events");
  		 let htmlResponse = "<ul>";
	     for(let i = 0; i < events.length; ++i){
		 let event = events[i];
		 htmlResponse += `<li><h3>ID ${event.eventId}</h3><h3>Name ${event.name}</li>`;

			}
  		 resultArea.innerHTML = htmlResponse;
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
