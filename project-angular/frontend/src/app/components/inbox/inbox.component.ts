import { Message } from './../../models/message';
import { MessageService } from './../../services/message.service';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-inbox',
    templateUrl: './inbox.component.html',
    styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {

    public messages: Message[];
    public selectedMessage: Message;

    constructor(private messageService: MessageService) { }

    ngOnInit() {
        const user = JSON.parse(localStorage.getItem('currentUser'));
        this.messageService.getMessages(user.id).subscribe(messages => {
            this.messages = messages;
        });
    }

    selectMessage(message: Message) {
        this.selectedMessage = message;
    }

}
