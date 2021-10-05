import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GetAllQuotationComponent } from './get-all-quotation.component';

describe('GetAllQuotationComponent', () => {
  let component: GetAllQuotationComponent;
  let fixture: ComponentFixture<GetAllQuotationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GetAllQuotationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GetAllQuotationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
