import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LayoutComponent } from './layouts/layout.component';
// Auth
import { AuthGuard } from './core/guards/auth.guard';


const routes: Routes = [
  { path: 'auth', loadChildren: () => import('./account/account.module').then(m => m.AccountModule) },
  { path: '', component: LayoutComponent, loadChildren: () => import('./pages/pages.module').then(m => m.PagesModule), canActivateChild: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
