import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Channel } from '../models/channel';
import {PlaylistEntry} from "../models/playlist-entry";
import {ChannelComment} from "../models/channel-comment";


@Injectable({
    providedIn: 'root'
})
export class ChannelService {

    constructor(private http: HttpClient) { }

    getAll(): Observable<Channel[]> {
        return this.http.get<Channel[]>('http://localhost:8080/resource/channel');

    }

    getChannel(displayId: string): Observable<Channel> {
        return this.http.get<Channel>(`http://localhost:8080/resource/channel/${displayId}`);
    }

    getChannelByUserId(id: number): Observable<{displayId: string}> {
        return this.http.get<{displayId: string}>(`http://localhost:8080/resource/channel/user/${id}`);
    }

    getCategories(): Observable<string[]> {
        return this.http.get<string[]>('http://localhost:8080/resource/channel/category');
    }

    saveChannel(channel: Channel): Observable<Channel> {
        return this.http.put<Channel>('http://localhost:8080/resource/channel', channel);
    }

    getPlaylist(displayId: string): Observable<PlaylistEntry[]> {
        return this.http.get<PlaylistEntry[]>(`http://localhost:8080/resource/channel/playlist/${displayId}`);
    }

    insertPlaylist(displayId: string, entry: PlaylistEntry, index: number): Observable<PlaylistEntry> {
        return this.http.post<PlaylistEntry>(`http://localhost:8080/resource/channel/playlist/${displayId}/${index}`, entry);
    }

    deletePlaylistEntry(displayId: string, index: number): Observable<PlaylistEntry> {
        return this.http.delete<PlaylistEntry>(`http://localhost:8080/resource/channel/playlist/${displayId}/${index}`);
    }

    getStatistics(displayId: string): Observable<any> {
        return this.http.get(`http://localhost:8080/resource/channel/statistics/${displayId}`);
    }

    getComments(displayId: string): Observable<ChannelComment[]> {
        return this.http.get<ChannelComment[]>(`http://localhost:8080/resource/comment/${displayId}`);
    }

    postComment(comment: ChannelComment): Observable<ChannelComment> {
        return this.http.post<ChannelComment>('http://localhost:8080/resource/comment', comment);
    }
}
