import { Component, OnInit } from '@angular/core';
import { DeviceDataTable } from 'src/app/shared/models/device.model';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';

declare const $: any;

@Component({
  selector: 'app-view-user',
  templateUrl: './view-user.component.html',
  styleUrls: ['./view-user.component.scss'],
})
export class ViewUserComponent implements OnInit {
  public dataTable: DeviceDataTable;

  constructor(
    private authService: AuthService,
    private router: Router,
    private notificationUtils: NotificationUtilsService
  ) {}

  ngOnInit(): void {
    this.loadData();
    this.dataTable = {
      headerRow: [
        'First Name',
        'Last Name',
        'Email',
        'NIC',
        'Contact',
        'Role',
        'Status',
        'Action'
      ],
      footerRow: [
        'First Name',
        'Last Name',
        'Email',
        'NIC',
        'Contact',
        'Role',
        'Status',
        'Action'
      ],
      dataRows: [],
    };
  }

  loadData() {
    this.notificationUtils.showMainLoading();
    this.authService.getUsersByRole().subscribe(
      (data) => {

        console.log(data)
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

  deleteUSer(id) {
    this.notificationUtils
      .promptConfirmation('This will remove selected user.')
      .then(
        () => {
          this.notificationUtils.showMainLoading();
          this.authService.deleteUser(id).subscribe(
            (data) => {
              this.notificationUtils.showSuccessMessage('User deleted.');
              this.notificationUtils.hideMainLoading();
              this.loadData();
            },
            (error) => {
              this.notificationUtils.showErrorMessage(error.message);
              this.notificationUtils.hideMainLoading();
            }
          );
        },
        () => {}
      );
  }

  editUser(id) {
    this.navigateWithQuery('/user/edit', id);
  }

  navigateWithQuery(path, id) {
    this.router.navigate([path], {
      queryParams: {
        userId: id,
      },
      queryParamsHandling: 'merge',
    });
  }
}
