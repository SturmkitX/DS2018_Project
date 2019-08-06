import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HistoryEntry} from "../models/history-entry";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

    constructor(private http: HttpClient) { }

    insertHistory(entry: HistoryEntry): Observable<HistoryEntry> {
        return this.http.post<HistoryEntry>('http://localhost:8080/resource/history', entry);
    }

}
