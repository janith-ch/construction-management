import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GetAllPaymentsComponent } from './get-all-payments.component';

describe('GetAllPaymentsComponent', () => {
  let component: GetAllPaymentsComponent;
  let fixture: ComponentFixture<GetAllPaymentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GetAllPaymentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GetAllPaymentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
