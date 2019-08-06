import { Channel } from '../../models/channel';
import { ChannelService } from '../../services/channel.service';
import { ActivatedRoute } from '@angular/router';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {Message} from "../../models/message";
import {User} from "../../models/user";
import {MessageService} from "../../services/message.service";
import {SocketMessage} from "../../models/socket-message";
import {HistoryEntry} from "../../models/history-entry";
import {HistoryService} from "../../services/history.service";
import {ChannelComment} from "../../models/channel-comment";

@Component({
    selector: 'app-view-channel',
    templateUrl: './view-channel.component.html',
    styleUrls: ['./view-channel.component.css']
})
export class ViewChannelComponent implements OnInit, OnDestroy {

    public channel: Channel;
    public composeEnabled = false;
    public message: Message;
    private ws: WebSocket;
    public commentSection = '';
    public debugInfo = '';
    public videoSource: string;
    private videoController: any;
    public userComment = '';

    private user: User;

    public userOwnsChannel = false;
    public channelComments: ChannelComment[];
    public postedComment = new ChannelComment();

    constructor(private route: ActivatedRoute,
                private channelService: ChannelService,
                private messageService: MessageService,
                private historyService: HistoryService) { }

    ngOnInit() {
        console.log('View channel OnInit');
        const displayId = this.route.snapshot.paramMap.get('id');
        this.user = JSON.parse(localStorage.getItem('currentUser'));
        console.log(this.user);
        this.channelService.getChannel(displayId).subscribe(channel => {
            this.channel = channel;

            this.message = new Message();
            const user: User = JSON.parse(localStorage.getItem('currentUser'));
            this.message.sender = user.email;
            this.message.senderName = user.name;
            this.message.receivers = [];
            this.message.receivers.push(this.channel.ownerId);
            this.message.id = 0;

            const envelope:any = {
                type: 'SUBSCRIBE',
                channel: this.channel.displayId,
                content: null
            };
            this.ws.send(JSON.stringify(envelope));
        });

        this.channelService.getChannelByUserId(this.user.id).subscribe(chan => {
            if (displayId === chan.displayId) {
                this.userOwnsChannel = true;
            }
        });

        this.channelService.getComments(displayId).subscribe(comms => {
            this.channelComments = comms;
        });

        // register the page visit
        const history = new HistoryEntry();
        history.id = 0;
        history.channelId = displayId;
        history.ownerId = this.user.id;

        // history channel name is not needed for conversion at server level
        // history access time is not needed, as it will be overwritten by the server
        this.historyService.insertHistory(history).subscribe(result => {
            if (!result) {
                console.log('Error registering page visit (history)');
            }
        });

        this.ws = new WebSocket(`ws://localhost:8080/insert`);

        this.ws.onmessage = event => {
            this.handleMessage(event.data);
        };

        this.videoController = document.getElementById('myVideo');
        this.videoController.oncanplay = () => {
            this.videoController.play();
        };


    }

    toggleCompose() {
        this.composeEnabled = !this.composeEnabled;
    }

    sendMessage() {
        console.log('Sending message....');
        console.log(this.message);
        this.messageService.saveMessage(this.message).subscribe(msg => {
            this.message = msg;
            console.log('Successfully sent message');
        });
    }

    sendComment() {
        if (this.userComment.length == 0) {
            return;
        }

        const envelope:any = {
            type: 'SEND',
            channel: this.channel.displayId,
            content: `${this.user.name} : ${this.userComment}`
        };
        this.ws.send(JSON.stringify(envelope));
    }

    handleMessage(data: string) {
        const envelope: SocketMessage = JSON.parse(data);
        if (envelope.type === 'COMMENT') {
            this.commentSection = this.commentSection.concat(envelope.content, '\n');
        } else if (envelope.type === 'SYNC') {
            // print debug info
            const params: string[] = envelope.content.split(' ');
            const shouldSync: boolean = Math.abs(parseInt(params[1]) - parseInt(this.videoController.currentTime)) > 5;
            console.log(parseInt(params[1]), parseInt(params[1]) - parseInt(this.videoController.currentTime));
            this.debugInfo = this.debugInfo.concat('User: ', String(this.videoController.currentTime), ' Server: ', params[1],
                ' Synchronize: ', shouldSync ? 'Yes' : 'No', '\n');

            // refresh the video info, if necessary
            if (this.videoSource !== params[0]) {
                this.videoSource = params[0];
                this.videoController.src = params[0];
                this.videoController.currentTime = parseFloat(params[1]);
            } else if (shouldSync) {
                this.videoController.currentTime = parseFloat(params[1]);
            }
        }
    }

    ngOnDestroy() {
        this.ws.close();
    }

    postComment() {
        this.postedComment.time = null;
        this.postedComment.authorId = this.user.id;
        this.postedComment.authorName = this.user.name;
        this.postedComment.channelId = this.channel.displayId;
        this.postedComment.id = 0;
        this.channelService.postComment(this.postedComment).subscribe(comm => {
            if (comm) {
                this.channelComments.push(comm);
            }
        });
    }

}
