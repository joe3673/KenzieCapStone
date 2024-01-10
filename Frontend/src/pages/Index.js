import BaseClass from "../util/baseClass";

class Index extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['logout'], this);

    }


    async mount() {
        document.getElementById("logout").onclick = this.logout;
        var stored = localStorage['unitybuildersuserinfo'];
        var link1 = document.getElementById("users");
        var link2 = document.getElementById("events");
        let user = 0;
        if(stored){
            user = JSON.parse(stored);
            link1.setAttribute('href', "UserProfile.html");
     	    document.getElementById("user").innerText="User profile";
            if(user.userType == "Organizer"){
                link2.setAttribute('href', "EventOrganizer.html");
            }
            else{
                link2.setAttribute('href', "EventViewUser.html");
            }
        }
        else{
            link1.setAttribute('href', "AccountSignIn.html");
     	    document.getElementById("user").innerText="Sign in";
            link2.setAttribute('href', "EventViewVisitor.html");
        }


    }

    async logout() {
        localStorage.removeItem('unitybuildersuserinfo')
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const index = new Index();
    index.mount();
};

window.addEventListener('DOMContentLoaded', main);
