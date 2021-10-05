import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuotationSummaryComponent } from './quotation-summary/quotation-summary.component';
import { QuotationRoutingModule } from './quotation-routing.module';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ViewOrderComponent } from './view-order/view-order.component';
import { GetAllQuotationComponent } from './get-all-quotation/get-all-quotation.component';
import { ViewQuotationComponent } from './view-quotation/view-quotation.component';



@NgModule({
  declarations: [QuotationSummaryComponent, ViewOrderComponent, GetAllQuotationComponent, ViewQuotationComponent],
  imports: [
    CommonModule,
    QuotationRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
  ]
})
export class QuotationModule { }
