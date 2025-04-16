import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbNavModule, NgbAccordionModule, NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { Routes, RouterModule } from '@angular/router';

// Swiper Slider
import { NgxUsefulSwiperModule } from 'ngx-useful-swiper';

// Counter
import { CountToModule } from 'angular-count-to';

import { BreadcrumbsComponent } from './breadcrumbs/breadcrumbs.component';

@NgModule({
  declarations: [
    BreadcrumbsComponent,
  ],
  imports: [
    CommonModule,
    NgbNavModule,
    NgbAccordionModule,
    NgbDropdownModule,
    NgxUsefulSwiperModule,
    CountToModule
  ],
  exports: [BreadcrumbsComponent,
    RouterModule
  ]
})
export class SharedModule { }
