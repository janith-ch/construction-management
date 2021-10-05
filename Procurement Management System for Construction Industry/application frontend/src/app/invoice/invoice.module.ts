import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GetAllInvoicesComponent } from './get-all-invoices/get-all-invoices.component';
import { InvoiceRoutingModule } from './invoice-routing.module';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { AddPaymentComponent } from './add-payment/add-payment.component';
import { GetAllPaymentsComponent } from './get-all-payments/get-all-payments.component';

@NgModule({
  declarations: [GetAllInvoicesComponent, AddPaymentComponent, GetAllPaymentsComponent],
  imports: [
    CommonModule,
    InvoiceRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
})
export class InvoiceModule {}
