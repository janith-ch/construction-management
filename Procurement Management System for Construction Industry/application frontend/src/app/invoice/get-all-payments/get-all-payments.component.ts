import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { InvoiceService } from 'src/app/services/invoice.service';
import { DeviceDataTable } from 'src/app/shared/models/device.model';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';
declare const $: any;

@Component({
  selector: 'app-get-all-payments',
  templateUrl: './get-all-payments.component.html',
  styleUrls: ['./get-all-payments.component.scss'],
})
export class GetAllPaymentsComponent implements OnInit {
  public dataTable: DeviceDataTable;
  constructor(
    private authService: AuthService,
    private router: Router,
    private notificationUtils: NotificationUtilsService,
    private invoiceservice: InvoiceService
  ) {}

  ngOnInit(): void {
    this.getData();
    this.dataTable = {
      headerRow: ['Invoice ID', 'Amount', 'Payment Method', 'Payment Date'],
      footerRow: ['Invoice ID', 'Amount', 'Payment Method', 'Payment Date'],
      dataRows: [],
    };
  }

  getData() {
    this.invoiceservice.getAllPayment().subscribe((data) => {
      this.initializeDataTable();
      this.dataTable.dataRows = data.dataBundle;
    });
  }

  initializeDataTable() {
    $(document).ready(() => {
      $('#dataTable').DataTable({
        pagingType: 'full_numbers',
        lengthMenu: [
          [10, 25, 50],
          [10, 25, 50],
        ],
        responsive: true,
        language: {
          search: '_INPUT_',
          searchPlaceholder: 'Search records',
        },
      });
    });
  }
}
