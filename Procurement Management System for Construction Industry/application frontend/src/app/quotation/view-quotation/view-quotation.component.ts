import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { QuotaionService } from 'src/app/services/quotaion.service';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';

@Component({
  selector: 'app-view-quotation',
  templateUrl: './view-quotation.component.html',
  styleUrls: ['./view-quotation.component.scss'],
})
export class ViewQuotationComponent implements OnInit {
  quotationId: string;
  quotation: any;
  order: any;

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private notificationUtils: NotificationUtilsService,
    private quotationservice: QuotaionService
  ) {
    this.quotationId = this.route.snapshot.queryParamMap.get('quotationId');
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.quotationservice
      .getQuotationDetails(this.quotationId)
      .subscribe((data) => {
        this.quotation = data.dataBundle;

        console.log(this.quotation);

        this.getOrder(this.quotation.orderId);
      });
  }

  getOrder(id) {
    this.quotationservice.gerOrderByID(id).subscribe((data) => {
      this.order = data.dataBundle;
    });
  }

  changeStatus(status) {
    this.quotationservice
      .updateQuotaionStatus(this.quotationId, status)
      .subscribe((data) => {
        this.router.navigateByUrl('/quotaion/get-all-quotations');
        this.notificationUtils.showSuccessMessage('Quotation Status Updated');
      });
  }
}
