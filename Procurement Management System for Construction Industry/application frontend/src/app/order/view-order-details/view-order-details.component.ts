import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { OrderService } from 'src/app/services/orderService';
import { QuotaionService } from 'src/app/services/quotaion.service';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';

@Component({
  selector: 'app-view-order-details',
  templateUrl: './view-order-details.component.html',
  styleUrls: ['./view-order-details.component.scss'],
})
export class ViewOrderDetailsComponent implements OnInit {
  orderID: string;
  order: any;

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private notificationUtils: NotificationUtilsService,
    private orderService: OrderService
  ) {
    this.orderID = this.route.snapshot.queryParamMap.get('orderID');
  }

  ngOnInit(): void {
    this.getData();
    console.log(this.orderID);
  }

  getData() {
    this.orderService.getOrderByID(this.orderID).subscribe((data) => {
      this.order = data.dataBundle;
    });
  }

  changeStatus(status) {
    this.orderService
      .updateOrderStatus(this.orderID, status)
      .subscribe((data) => {
        this.router.navigateByUrl('/order/view');
        this.notificationUtils.showSuccessMessage('Order Status Updated');
      });
  }
}
