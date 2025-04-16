import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalService {

  public saveData(key: string, value: string) {
    localStorage.setItem(key, value);
  }

  public getData(key: string): string {
    var val = localStorage.getItem(key);
    return (val !== null ? val : "");
  }
  public removeData(key: string) {
    localStorage.removeItem(key);
  }

  public clearData() {
    localStorage.clear();
  }
}
