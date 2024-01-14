import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/EventClient";
import UserClient from "../api/UserClient";

/*
 EventPageUser. Handles javascript for the
 Event User page.
 */

class EventPageUser extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['loadEvents', 'displayEvents', 'addEvent'], this);
        this.dataStore = new DataStore();
    }

 async mount() {

        this.client = new EventClient();

        console.log(localStorage['unitybuildersuserinfo'])
        document.getElementById('add-form').addEventListener('submit', this.addEvent);
        var stored = localStorage['unitybuildersuserinfo'];

        let user = 0;

        let attendedEvents = 0;

        if(stored){

            user = JSON.parse(stored);

            attendedEvents = user.eventsList;

        }



        let resultArea = document.getElementById("attendedevents");

        let htmlResponse = "<ul>";

        for(let i = 0; i < attendedEvents.length; ++i){

            let event = attendedEvents[i];

            htmlResponse += `<li><h3>ID ${event}</h3></li>`;



        }

        resultArea.innerHTML = htmlResponse;



        await this.loadEvents(attendedEvents);

    }

    async loadEvents(attendedEvents) {
        // get all events,sponsored events, &attended events from backend
        const allEvents = await this.client.getAllEvents();


        //Display events in the corresponding boxes
        this.displayEvents('all-events-box', allEvents);
    }
    async getEventsAttendedByFriends(userId, errorCallback) {
        try {
            const response = await this.client.get(`/User/${userId}/FriendList/Events`);
            return response.data;
        } catch (error) {
            this.handleError("GetEventsAttendedByFriends", error, errorCallback);
        }
    }


    displayEvents(containerId, events) {
        //events in a scrolling list inside th container
        const container = document.getElementById(containerId);
         if (container) {

              const eventList = container.querySelector('ul');

              if (eventList) {

                  events.forEach(event => {

                      let listItem = document.createElement('li');

                      listItem.textContent = "ID: " + event.eventId;

                      eventList.appendChild(listItem);

                      listItem = document.createElement('li');

                      listItem.textContent = "Name: " + event.name;

                      eventList.appendChild(listItem);

                  });

              }

          }

      }


async addEvent(event) {
    event.preventDefault();

    let client2 = new UserClient();
    let id = document.getElementById("id").value;

    var stored = localStorage['unitybuildersuserinfo'];
    let user = 0;
    let attendedEvents = 0;

    if (stored) {
        user = JSON.parse(stored);
    }

    await client2.joinEvent(user.username, id, this.errorHandler);

    // Corrected: Use await to wait for the completion of getUser
    user = await client2.getUser(user.username, this.errorHandler);

    // Corrected: Update the attendedEvents after joining the event
    attendedEvents = user.eventsList;

    let resultArea = document.getElementById("attendedevents");
    let htmlResponse = "<ul>";

    for (let i = 0; i < attendedEvents.length; ++i) {
        let event = attendedEvents[i];
        htmlResponse += `<li><h3>ID ${event}</h3></li>`;
    }

    resultArea.innerHTML = htmlResponse;

    // Corrected: Load events after joining
    await this.loadEvents(attendedEvents);
}
}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const eventPageUser = new EventPageUser();
    eventPageUser.mount();
};

window.addEventListener('DOMContentLoaded', main);
