import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

// Auth Services
import { AuthenticationService } from 'src/app/services/auth.service';
import { LocalService } from 'src/app/services/local.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard {
  constructor(public authenticationService: AuthenticationService,
    public route: Router, public local: LocalService) {
    console.log('Application Gaurd Active...')
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (localStorage.getItem('currentUser') != null) {
      const userObject = JSON.parse(this.local.getData('currentUser'));
      console.log(state.url);

      if (state.url !== '' && state.url !== '/' && state.url !== '/auth/login' && state.url !== '/access-denied') {
        console.log(userObject?.rights);

        state.url = (state.url.split("/").length > 1 ? state.url.split("/")[1] : "")
        state.url = state.url.replace("-", " ");
        for (let right of userObject?.rights) {
          for (let child of right?.children) {
            // const pattern = new RegExp(`^${child.url.replace(':id', '\\d+')}$`);
            // console.log(pattern);
            console.log(state.url);

            child.url = (child.url.split("/").length > 1 ? child.url.split("/")[1] : "")
            child.url = child.url.replace("-", " ");
            // if (state.url.includes(child.url)) {
              // }
            }
          }
          return true;
        // this.route.navigate(['/access-denied']);
        // return false;
      } else {
        return true;
      }
    } else {
      this.route.navigate(['/auth/login']);
      return false;
    }
  }
}
