import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { OrderService } from 'src/app/services/orderService';
import { DeviceDataTable } from 'src/app/shared/models/device.model';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';

declare const $: any;
@Component({
  selector: 'app-view-order',
  templateUrl: './view-order.component.html',
  styleUrls: ['./view-order.component.scss'],
})
export class ViewOrderComponent implements OnInit {
  public dataTable: DeviceDataTable;

  constructor(
    private authService: AuthService,
    private router: Router,
    private notificationUtils: NotificationUtilsService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.loadData();

    this.dataTable = {
      headerRow: [
        'Order ID',
        'Created By',
        'Date Period',
        'Total Cost',
        'Status',
        'Action',
      ],
      footerRow: [
        'Order ID',
        'Created By',
        'Date Period',
        'Total Cost',
        'Status',
        'Action',
      ],
      dataRows: [],
    };
  }

  loadData() {
    this.notificationUtils.showMainLoading();
    this.orderService.getAllOrders().subscribe(
      (data) => {
        console.log(data);
        this.destroyDataTable();
        this.dataTable.dataRows = data.dataBundle;
        this.notificationUtils.hideMainLoading();
        this.initializeDataTable();
      },
      (error) => {
        this.notificationUtils.showErrorMessage(error.message);
        this.notificationUtils.hideMainLoading();
      }
    );
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

  ViewOrder(id) {
    console.log('hi');
    this.navigateWithQuery('/order/view-detail', id);
  }

  navigateWithQuery(path, id) {
    this.router.navigate([path], {
      queryParams: {
        orderID: id,
      },
      queryParamsHandling: 'merge',
    });
  }
}
