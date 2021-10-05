import { QuotaionService } from 'src/app/services/quotaion.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';

@Component({
  selector: 'app-view-order',
  templateUrl: './view-order.component.html',
  styleUrls: ['./view-order.component.scss'],
})
export class ViewOrderComponent implements OnInit {
  orderID: string;
  order: any;

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private notificationUtils: NotificationUtilsService,
    private quotationservice: QuotaionService
  ) {
    this.orderID = this.route.snapshot.queryParamMap.get('orderID');
  }

  ngOnInit(): void {
    this.getData();

    console.log(this.orderID);
  }

  getData() {
    this.quotationservice.gerOrderByID(this.orderID).subscribe((data) => {
      this.order = data.dataBundle;
    });
  }

  changeStatus(status) {
    this.quotationservice
      .updateOrderStatus(this.orderID, status)
      .subscribe((data) => {
        this.router.navigateByUrl('/quotaion/summary');
        this.notificationUtils.showSuccessMessage('Order Status Updated');
      });
  }
}
