import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { LayoutsModule } from "./layouts/layouts.module";
import { PagesModule } from "./pages/pages.module";

// Auth
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { environment } from '../environments/environment';

// Language
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { FormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { SharedModule } from './shared/shared.module';
import { NgxUiLoaderConfig, NgxUiLoaderModule, PB_DIRECTION, POSITION, SPINNER } from 'ngx-ui-loader';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import { ConfirmationModalComponent } from './common/confirmation-modal/confirmation-modal.component';
import { AddDepartmentComponent } from './pages/departments/add-department/add-department.component';
import { DiffPipe } from './helpers/diff.pipe';
import { JwtInterceptor } from './helpers/jwt.interceptor';
import { ErrorInterceptor } from './helpers/error.interceptor';
import { TitleCasePipe } from '@angular/common';
import { ToastsContainer } from './account/toasts/toasts-container.component';
import { NgbToastModule } from '@ng-bootstrap/ng-bootstrap';
import { ParseDatePipe } from './helpers/parse-date.pipe';
import { SpecialPipePipe } from './common/special-pipe.pipe';

export function createTranslateLoader(http: HttpClient): any {
  return new TranslateHttpLoader(http, 'assets/i18n/', '.json');
}

// initializeApp(environment.firebase);
const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  bgsColor: 'red',
  bgsPosition: POSITION.bottomCenter,
  bgsSize: 50,
  bgsType: SPINNER.rectangleBounce, // background spinner type
  fgsType: SPINNER.rectangleBounce, // foreground spinner type
  pbDirection: PB_DIRECTION.leftToRight, // progress bar direction
  pbThickness: 10, // progress bar thickness
};

@NgModule({
  declarations: [
    AppComponent,   
    AccessDeniedComponent,
    ConfirmationModalComponent,
    ParseDatePipe,
    SpecialPipePipe
  ],
  imports: [
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
    BrowserAnimationsModule,
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    LayoutsModule,
    PagesModule,
    FormsModule,
    NgSelectModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig)
  ], providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    DiffPipe,
    TitleCasePipe
  ], bootstrap: [AppComponent]
})
export class AppModule { }
