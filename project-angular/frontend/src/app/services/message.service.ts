import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Message } from '../models/message';

@Injectable({
    providedIn: 'root'
})
export class MessageService {

    constructor(private http: HttpClient) { }

    getMessages(id: number): Observable<Message[]> {
        return this.http.get<Message[]>(`http://localhost:8080/resource/message/${id}`);
    }

    saveMessage(message: Message): Observable<Message> {
        return this.http.post<Message>('http://localhost:8080/resource/message', message);
    }
}
