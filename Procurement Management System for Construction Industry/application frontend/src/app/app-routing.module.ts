import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';
import { AuthComponent } from './layouts/auth/auth.component';
import { AdminLayoutComponent } from './layouts/admin/admin-layout.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: '',
    component: AuthComponent,
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./auth/auth.module').then((m) => m.AuthModule),
      },
    ],
  },
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: 'user',
        loadChildren: () =>
          import('./user/user.module').then((m) => m.UserModule),
      },
    ],
  },
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: 'quotation',
        loadChildren: () =>
          import('./quotation/quotation.module').then((m) => m.QuotationModule),
      },
    ],
  },
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: 'invoice',
        loadChildren: () =>
          import('./invoice/invoice.module').then((m) => m.InvoiceModule),
      },
    ],
  },
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes, {
      useHash: true,
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
