import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GetAllInvoicesComponent } from './get-all-invoices/get-all-invoices.component';
import { AddPaymentComponent } from './add-payment/add-payment.component';
import { GetAllPaymentsComponent } from './get-all-payments/get-all-payments.component';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: '', redirectTo: 'summary' },
      {
        path: 'summary',
        component: GetAllInvoicesComponent,
      },
    ],
  },
  {
    path: '',
    children: [
      { path: '', redirectTo: 'add-payment' },
      {
        path: 'add-payment',
        component: AddPaymentComponent,
      },
    ],
  },
  {
    path: '',
    children: [
      { path: '', redirectTo: 'get-all-payments' },
      {
        path: 'get-all-payments',
        component: GetAllPaymentsComponent,
      },
    ],
  },
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class InvoiceRoutingModule {}
