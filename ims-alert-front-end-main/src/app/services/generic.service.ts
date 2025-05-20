import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalService } from './local.service';
import { map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { GlobalComponent } from '../global-component';

@Injectable({
  providedIn: 'root'
})
export class GenericService {
  constructor(public http: HttpClient, public local: LocalService) { }
  
  getCompanyId() {
    const userSubject = (this.local.getData('currentUser') != "" ? JSON.parse(this.local.getData('currentUser')) : null);
    return (userSubject.companyId !== '' ? userSubject.companyId : "0");
  }

  getUserId() {
    const userSubject = (this.local.getData('currentUser') != "" ? JSON.parse(this.local.getData('currentUser')) : null);
    return (userSubject.id !== '' ? userSubject.id : "0");
  }

  saveData(url: any, obj: any): Observable<any> {
    obj['companyId'] = this.getCompanyId();
    obj['updatedBy'] = this.getUserId();
    obj['createdBy'] = this.getUserId();
    return this.http.post<any>(environment.apiUrl + url, obj)
      .pipe(map(list => {
        return list;
      }));
  }

  putData(url: any, obj: any): Observable<any> {
    // obj['companyId'] = this.getCompanyId();
    // obj['updatedBy'] = this.getUserId();
    // obj['createdBy'] = this.getUserId();
    return this.http.put<any>(environment.apiUrl + url, obj)
      .pipe(map(list => {
        return list;
      }));
  }

  deleteData(url: any): Observable<any> {
    return this.http.delete<any>(environment.apiUrl + url)
      .pipe(map(list => {
        return list;
      }));
  }

  getData(url: any, isCompanyId?: any): Observable<any> {
    url = environment.apiUrl + url;
    if (isCompanyId) {
      url += "/" + this.getCompanyId();
    }
    return this.http.get<any>(url)
      .pipe(map(list => {
        return list;
      }));
  }

  postData(url: any, obj: any): Observable<any> {
    obj['companyId'] = this.getCompanyId();
    obj['updatedBy'] = this.getUserId();
    obj['createdBy'] = this.getUserId();
    return this.http.post<any>(environment.apiUrl + url, obj)
      .pipe(map(list => {
        return list;
      }));
  }
}
