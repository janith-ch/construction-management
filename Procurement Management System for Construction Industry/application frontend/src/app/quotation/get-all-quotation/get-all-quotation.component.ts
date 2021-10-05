import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { QuotaionService } from 'src/app/services/quotaion.service';
import { DeviceDataTable } from 'src/app/shared/models/device.model';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';
declare const $: any;

@Component({
  selector: 'app-get-all-quotation',
  templateUrl: './get-all-quotation.component.html',
  styleUrls: ['./get-all-quotation.component.scss'],
})
export class GetAllQuotationComponent implements OnInit {
  public dataTable: DeviceDataTable;
  constructor(
    private authService: AuthService,
    private router: Router,
    private notificationUtils: NotificationUtilsService,
    private quotationservice: QuotaionService
  ) {}

  ngOnInit(): void {
    this.getData();
    this.dataTable = {
      headerRow: ['Order ID', 'Date Period', 'No of Quotations', 'Action'],
      footerRow: ['Order ID', 'Date Period', 'No of Quotations', 'Action'],
      dataRows: [],
    };
  }

  getData() {
    this.quotationservice.getQuotations().subscribe((data) => {
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

  ViewQuotation(id) {
    this.navigateWithQuery('/quotation/view-quotation', id);
  }

  navigateWithQuery(path, id) {
    this.router.navigate([path], {
      queryParams: {
        quotationId: id,
      },
      queryParamsHandling: 'merge',
    });
  }
}
