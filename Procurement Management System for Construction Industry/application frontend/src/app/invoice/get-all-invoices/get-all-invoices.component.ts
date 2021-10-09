import { InvoiceService } from './../../services/invoice.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { DeviceDataTable } from 'src/app/shared/models/device.model';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';
declare const $: any;

@Component({
  selector: 'app-get-all-invoices',
  templateUrl: './get-all-invoices.component.html',
  styleUrls: ['./get-all-invoices.component.scss'],
})
export class GetAllInvoicesComponent implements OnInit {
  public dataTable: DeviceDataTable;
  invoices: any;
  constructor(
    private authService: AuthService,
    private router: Router,
    private notificationUtils: NotificationUtilsService,
    private invoiceservice: InvoiceService
  ) {}

  ngOnInit(): void {
    this.dataTable = {
      headerRow: [
        'Invoice ID',
        'Order ID',
        'Site Name',
        'Matireal Name',
        'Amount',
        'Status',
        'Action',
      ],
      footerRow: [
        'Invoice ID',
        'Order ID',
        'Site Name',
        'Matireal Name',
        'Amount',
        'Status',
        'Action',
      ],
      dataRows: [],
    };

    this.getData();
  }

  getData() {
    this.invoiceservice.getAllInvoices().subscribe((data) => {
      this.initializeDataTable();
      this.dataTable.dataRows = data.dataBundle;
    });
  }

  destroyDataTable() {
    $('#dataTable').DataTable().destroy();
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

  addPayment(invoiceId) {
    this.navigateWithQuery('/invoice/add-payment', invoiceId);
  }

  navigateWithQuery(path, id) {
    this.router.navigate([path], {
      queryParams: {
        invoiceId: id,
      },
      queryParamsHandling: 'merge',
    });
  }
}
