import { Component, OnInit } from '@angular/core';
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {HistoryEntry} from "../../models/history-entry";

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

    public user: User;
    public password1 = '';
    public password2 = '';

    public userHistory: HistoryEntry[];
    public isHistoryVisible = false;

    constructor(private userService: UserService) {}

    ngOnInit(): void {
        const userId = JSON.parse(localStorage.getItem('currentUser')).id;
        this.userService.findUser(userId).subscribe(user => {
            this.user = user;
        });

        this.userService.getUserHistory(userId).subscribe(history => {
            this.userHistory = history;
        });
    }

    saveUser() {
        if (this.password1.length > 0) {
            if (this.password1 != this.password2) {
                alert("The passwords do not match!");
                return;
            }

            if (this.password1.length < 8) {
                alert("The password must be at least 8 characters long!");
                return;
            }
        }

        this.user.password = this.password1;

        this.userService.updateUser(this.user).subscribe(user => {
            this.user = user;
        }, error => {
            console.log(error);
        });
    }

    toggleHistoryVisible() {
        this.isHistoryVisible = !this.isHistoryVisible;
    }

    toggleEmailsActive() {
        this.user.enableEmails = !this.user.enableEmails;
    }


}
