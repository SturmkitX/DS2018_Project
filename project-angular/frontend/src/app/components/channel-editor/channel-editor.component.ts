import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {PlaylistEntry} from "../../models/playlist-entry";
import {ChannelService} from "../../services/channel.service";
import {Channel} from "../../models/channel";

@Component({
  selector: 'app-channel-editor',
  templateUrl: './channel-editor.component.html',
  styleUrls: ['./channel-editor.component.css']
})
export class ChannelEditorComponent implements OnInit, OnChanges {

    public videoSource = "";
    public playlist: PlaylistEntry[];
    private selected: PlaylistEntry;

    @Input() channel: Channel;

    constructor(private channelService: ChannelService) { }

    ngOnInit() {
    }

    setSelected(entry: PlaylistEntry) {
        this.selected = entry;
    }

    isSelected(entry: PlaylistEntry): boolean {
        return this.selected === entry;
    }

    addSource() {
        const index = this.selected ? this.playlist.indexOf(this.selected) + 1 : this.playlist.length;
        const video = document.createElement('video');
        video.src = this.videoSource;
        video.oncanplay = () => {
            const entry = new PlaylistEntry();
            entry.url = this.videoSource;
            entry.duration = video.duration;
            this.channelService.insertPlaylist(this.channel.displayId, entry, index).subscribe(result => {
                this.playlist.splice(index, 0, result);
            });
        }
    }

    removeSelected() {
        const index = this.playlist.indexOf(this.selected);
        this.channelService.deletePlaylistEntry(this.channel.displayId, index)
            .subscribe(removed => {
                if (removed) {
                    // remove
                    this.playlist.splice(index, 1);
                }
            });
    }

    ngOnChanges(changes: SimpleChanges): void {
        console.log('Input changes');
        console.log(changes);
        if (this.channel == null) {
            return;
        }

        this.channelService.getPlaylist(this.channel.displayId).subscribe(playlist => {
            this.playlist = playlist;
            console.log(playlist);
        });
    }

}
