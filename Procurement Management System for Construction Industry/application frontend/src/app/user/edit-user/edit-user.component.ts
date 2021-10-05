import { Component, OnInit } from '@angular/core';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss'],
})
export class EditUserComponent implements OnInit {
  userForm: FormGroup;
  userId: string;

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private notificationUtils: NotificationUtilsService
  ) {
    this.userId = this.route.snapshot.queryParamMap.get('userId');
  }

  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      nic: [null],
      roleId: [null],
      mobile: [
        null,
        [
          Validators.required,
          Validators.maxLength(10),
          Validators.minLength(10),
        ],
      ],
    });
    this.loadUserData();
  }

  get user() {
    return this.userForm.controls;
  }

  updateEmployee() {
    this.notificationUtils.promptConfirmation().then(
      () => {
        this.notificationUtils.showMainLoading();
        this.authService.editUser(this.userId, this.userForm.value).subscribe(
          () => {
            this.notificationUtils.hideMainLoading();
            this.router.navigateByUrl('/user/view');
            this.notificationUtils.showSuccessMessage('Employee updated.');
          },
          (error) => {
            this.notificationUtils.hideMainLoading();
            this.notificationUtils.showErrorMessage(
              'Error updating employee : ' + error.message
            );
          }
        );
      },
      () => {}
    );
  }

  loadUserData() {
    this.notificationUtils.showMainLoading();
    this.authService.getUserById(this.userId).subscribe(
      (data) => {
        this.notificationUtils.hideMainLoading();
        this.userForm.patchValue({
          email: data.data.email,
          first_name: data.data.first_name,
          last_name: data.data.last_name,
          address1: data.data.address1,
          address2: data.data.address2,
          address3: data.data.address3,
          advance_amount: data.data.advance_amount,
          city: data.data.city,
          postal: data.data.postal,
          contact: data.data.contact,
        });
      },
      (error) => {
        this.notificationUtils.hideMainLoading();
        this.notificationUtils.showErrorMessage(
          'Error loading work : ' + error.message
        );
      }
    );
  }
}
