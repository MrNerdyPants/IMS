import {Injectable} from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';

import {Router} from '@angular/router';
import { AuthenticationService } from '../services/auth.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private authService: AuthenticationService, private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(tap(() => {
      },
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status !== 401) {
            return;
          }
          this.authService.logout();
          this.router.navigate(['/auth/login']);
        }
      }));
  }
}
