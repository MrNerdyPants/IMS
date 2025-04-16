import { Injectable } from '@angular/core';
import { User } from '../core/models/auth.models';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { GlobalComponent } from '../global-component';
import { environment } from 'src/environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalService } from './local.service';


const TOKEN_KEY = 'auth-token';
const USER_KEY = 'currentUser';
const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })

/**
 * Auth-service Component
 */
export class AuthenticationService {

    public currentUser: any;
    private currentThemeSubject = new BehaviorSubject<string>('');
    theme = this.currentThemeSubject.asObservable();


    constructor(public http: HttpClient, private route: ActivatedRoute, private router: Router,
        public local: LocalService) {
        this.currentUser = (local.getData('currentUser') != "" ? JSON.parse(local.getData('currentUser')) : null);
        this.currentThemeSubject = new BehaviorSubject<string>(local.getData('selectedTheme'));
        this.theme = this.currentThemeSubject.asObservable();
    }

    public get currentUserValue(): any {
        const userSubject = (this.local.getData('currentUser') != "" ? JSON.parse(this.local.getData('currentUser')) : null);
        return userSubject;
    }

    currentThemeValue(theme: string): any {
        this.currentThemeSubject.next(theme);
    }


    // login(userName: string, password: string): Observable<User> {
    //   const pass = Md5.init(password);
    //   const body = {username: userName, password: pass};
    //   return this.http.post<any>(environment.apiUrl + 'login/authenticate', body)
    //     .pipe(map(user => {
    //       // store user details and jwt token in local storage to keep user logged in between page refreshes
    //       this.currentUserSubject.next(user);
    //       return user;
    //     }));
    // }

    login(user: any): void {
        console.log("user: " + JSON.stringify(user));
        localStorage.setItem('currentUser', JSON.stringify(user));
        localStorage.setItem(TOKEN_KEY, user?.token);
        localStorage.setItem('menuloaded', 'No');
        // this.currentUserSubject.next(user);
        // this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(this.local.getData('currentUser')) || null);
        // this.currentUser = this.currentUserSubject.asObservable();
        // const roleList: any = [];
        // for (const rr of this.currentUserValue.employeeRole) {
        //   roleList.push(rr.role.roleId);
        // }
        // const role = {
        //   companyId: this.currentUserValue.companyId, roleList
        // };

        // this.sideMenuService.getSideMenu(role).subscribe(
        //   data => {
        //     console.log('side menu', data);
        //     this.sideMenuService.changeMenu(data);
        //   },
        //   error => {
        //     console.error(error);
        //   });

        // this.sideMenuService.getCurrentUrlRight();
    }


    verifyLogin(userName: string, password: string): Observable<any> {
        const pass = password;
        const body = { username: userName.trim(), password: pass };

        return this.http.post<any>(environment.apiUrl + 'auth/authenticate', body);
    }

    sendResetLink(userName: string): Observable<any> {
        const body = { username: userName.trim() };

        return this.http.post<any>(environment.apiUrl + 'setup/sendResetLink', body);
    }

    validateToken(token: string): Observable<any> {
        const body = { token };

        return this.http.post<any>(environment.apiUrl + 'setup/isValidToken', body);
    }

    resetPassword(token: string, password: any): Observable<any> {
        const body = { token, password };

        return this.http.post<any>(environment.apiUrl + 'setup/resetUserPassword', body);
    }


    logout(): void {
        localStorage.removeItem(USER_KEY);
        localStorage.removeItem(TOKEN_KEY);
    }
}

