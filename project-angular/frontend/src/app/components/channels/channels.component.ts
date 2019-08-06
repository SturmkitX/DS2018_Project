import { Channel } from '../../models/channel';
import { ChannelService } from '../../services/channel.service';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-channels',
    templateUrl: './channels.component.html',
    styleUrls: ['./channels.component.css']
})
export class ChannelsComponent implements OnInit {

    public channels: Channel[];
    public shownChannels: Channel[];
    public nameSearchField = '';

    constructor(private channelService: ChannelService) { }

    ngOnInit() {
        this.channelService.getAll().subscribe(data => {
            this.channels = data;
            this.shownChannels = data;
        });
    }

    filterChannels() {
        this.shownChannels = [];
        console.log(this.channels.length);
        for (let channel of this.channels) {
            if (channel.name.toLowerCase().includes(this.nameSearchField.toLowerCase())) {
                this.shownChannels.push(channel);
            }
        }
    }

}
