import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { QuotationSummaryComponent } from './quotation-summary/quotation-summary.component';
import { ViewOrderComponent } from './view-order/view-order.component';
import { GetAllQuotationComponent } from './get-all-quotation/get-all-quotation.component';
import { ViewQuotationComponent } from './view-quotation/view-quotation.component';
const routes: Routes = [
  {
    path: '',
    children: [
      { path: '', redirectTo: 'summary' },
      {
        path: 'summary',
        component: QuotationSummaryComponent,
      },
    ],
  },
  {
    path: '',
    children: [
      { path: '', redirectTo: 'view-order/' },
      {
        path: 'view-order',
        component: ViewOrderComponent,
      },
    ],
  },
  {
    path: '',
    children: [
      { path: '', redirectTo: 'get-all-quotations' },
      {
        path: 'get-all-quotations',
        component: GetAllQuotationComponent,
      },
    ],
  },
  {
    path: '',
    children: [
      { path: '', redirectTo: 'view-quotation' },
      {
        path: 'view-quotation',
        component: ViewQuotationComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class QuotationRoutingModule {}
