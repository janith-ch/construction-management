import { InvoiceService } from './../../services/invoice.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';

@Component({
  selector: 'app-add-payment',
  templateUrl: './add-payment.component.html',
  styleUrls: ['./add-payment.component.scss'],
})
export class AddPaymentComponent implements OnInit {
  invoiceId: any;
  paymentForm: any;

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private notificationUtils: NotificationUtilsService,
    private formBuilder: FormBuilder,
    private invoiceservice: InvoiceService
  ) {
    this.invoiceId = this.route.snapshot.queryParamMap.get('invoiceId');
  }

  ngOnInit(): void {
    this.paymentForm = this.formBuilder.group({
      amount: [null, [Validators.required]],
      paymentMethod: [null, [Validators.required]],
      paymentDate: [null, [Validators.required]],
    });
  }

  get payment() {
    return this.paymentForm.controls;
  }

  createPayment() {
    this.notificationUtils.promptConfirmation().then(
      () => {
        this.notificationUtils.showMainLoading();
        this.paymentForm.value.invoiceId = this.invoiceId;

        this.invoiceservice.addPayment(this.paymentForm.value).subscribe(
          () => {
            this.paymentForm.reset();
            this.router.navigateByUrl('/invoice/get-all-payments');
            this.notificationUtils.hideMainLoading();
            this.notificationUtils.showSuccessMessage('Payment Added.');
          },
          (error) => {
            this.notificationUtils.hideMainLoading();
            this.notificationUtils.showErrorMessage(
              'Error creating Paymnet : ' + error.message
            );
          }
        );
      },
      () => {}
    );
  }
}
