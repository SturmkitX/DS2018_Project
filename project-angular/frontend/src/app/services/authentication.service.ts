import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    constructor(private http: HttpClient) { }

    login(email: string, password: string) {
        const btoa = window.btoa(email + ':' + password);
        return this.http.post<any>(`http://localhost:8080/login`, null, {
            headers: {
                Authorization: `Basic ${btoa}`
            }
        }).pipe(map(user => {
            // login successful if there's a user in the response
            if (user) {
                // store user details and basic auth credentials in local storage 
                // to keep user logged in between page refreshes
                user.authdata = window.btoa(email + ':' + password);
                localStorage.setItem('currentUser', JSON.stringify(user));
            }

            return user;
        }));
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
    }
}