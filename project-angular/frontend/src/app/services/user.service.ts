import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../models/user";
import {HistoryEntry} from "../models/history-entry";

@Injectable({
  providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) { }

    findUser(id: number): Observable<User> {
        return this.http.get<User>(`http://localhost:8080/resource/user/${id}`);
    }

    updateUser(user: User): Observable<User> {
        return this.http.put<User>('http://localhost:8080/resource/user', user);
    }

    insertUser(user: User): Observable<User> {
        return this.http.post<User>('http://localhost:8080/signup', user);
    }

    activateUser(id: number): Observable<User> {
        return this.http.get<User>(`http://localhost:8080/resource/user/activate/${id}`);
    }

    getUserHistory(id: number): Observable<HistoryEntry[]> {
        return this.http.get<HistoryEntry[]>(`http://localhost:8080/resource/history/${id}`);
    }

}
