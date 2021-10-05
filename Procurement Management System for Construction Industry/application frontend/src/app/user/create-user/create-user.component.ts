import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationUtilsService } from 'src/app/utils/notification-utils.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss'],
})
export class CreateUserComponent implements OnInit {
  userForm: FormGroup;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router,
    private notificationUtils: NotificationUtilsService
  ) {}

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
  }

  get user() {
    return this.userForm.controls;
  }

  createEmployee() {
    this.notificationUtils.promptConfirmation().then(
      () => {
        this.notificationUtils.showMainLoading();
        this.userForm.value.isActive = 1

        console.log(this.userForm.value)
        this.authService.registerEmployee(this.userForm.value).subscribe(
          () => {
            this.userForm.reset();
            this.router.navigateByUrl('/user/view');
            this.notificationUtils.hideMainLoading();
            this.notificationUtils.showSuccessMessage('Employee registered.');
          },
          (error) => {
            this.notificationUtils.hideMainLoading();
            this.notificationUtils.showErrorMessage(
              'Error creating employee : ' + error.message
            );
          }
        );
      },
      () => {}
    );
  }
}
