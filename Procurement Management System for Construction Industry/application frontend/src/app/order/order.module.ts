import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrderRoutingModule } from './order-routing.module';
import { ViewOrderComponent } from './view-order/view-order.component';
import { ViewOrderDetailsComponent } from './view-order-details/view-order-details.component';


@NgModule({
  declarations: [ViewOrderComponent, ViewOrderDetailsComponent],
  imports: [
    CommonModule,
    OrderRoutingModule
  ]
})
export class OrderModule { }
