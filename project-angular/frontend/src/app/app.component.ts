import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    public title = 'DS project home page';
    public loggedIn = false;

    constructor() {  }

    ngOnInit() {
        if (localStorage.getItem('currentUser') != null) {
            this.loggedIn = true;
        }
    }

    checkLogIn(status: boolean) {
        this.loggedIn = status;
    }

}
