import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import EventClient from "../api/EventClient";
import UserClient from "../api/UserClient";

/*
 EventPageUser. Handles javascript for the
 Event User page.

 ***I was trying something here but I will probably go back to my previous version, please ignore for now.
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

    async addEvent(event){
        event.preventDefault()
        let client2 = new UserClient()
        let id = document.getElementById("eventId").value;
        var stored = localStorage['unitybuildersuserinfo'];
        let user = 0;
        let attendedEvents = 0;
        if(stored){
            user = JSON.parse(stored);
        }
        console.log(user.username)

        await client2.joinEvent(user.username, id, this.errorHandler)


        user = client2.getUser(user.username, this.errorHandler)
        localStorage['unitybuildersuserinfo'] = JSON.stringify(user);
    }



    /*
    async onGetAll(event){
        event.preventDefault();
        this.dataStore.set("EventList", null);
        const eventList = await this.client.getAllGames(this.errorHandler);
        this.dataStore.set("EventList", eventList);
        if (eventList) {


            this.showMessage('Retrieved all events')
            let resultArea = document.getElementById("allEvents");
		    let htmlResponse = "<ul>";

			/*for(let i = 0; i < eventList.length; ++i){
				let event = eventList[i];
				htmlResponse += `<li><h3>ID ${event.id}</h3><h3>Name ${event.name}</h3><h3>location ${event.location}</h3><h3>Start time ${event.startTime}</h3><h3>End time ${event.endTime}</h3><h3>Sponsor ${event.eventSponsor}</h3></li>`;
			}

			resultArea.innerHTML = htmlResponse;
           document.getElementById("eventById").textContent = ""

        }
        else {
            this.errorHandler("Error Retrieving!  Try again...");
        }
    }

     async onGetEvent(){
         this.dataStore.set("Event", null);
         let id = document.getElementById("id-field").value;
         const event = await this.client.getEvent(id, this.errorHandler);
         this.dataStore.set("Event", event);
         if (event) {
             this.showMessage('Retrieved Event')
              let resultArea = document.getElementById("eventById");
   		    let htmlResponse = "<ul>";

   			htmlResponse += `<li><h3>ID ${event.id}</h3><h3>Name ${event.name}</h3><h3>location ${event.location}</h3><h3>Start time ${event.startTime}</h3><h3>End time ${event.endTime}</h3><h3>Sponsor ${event.eventSponsor}</h3></li>`;

   			resultArea.innerHTML = htmlResponse;
            document.getElementById("allEvents").textContent = ""
         }
         else {
             this.errorHandler("Error Retrieving!  Try again...");
         }

     }

        */
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const eventPageUser = new EventPageUser();
    eventPageUser.mount();
};

window.addEventListener('DOMContentLoaded', main);
