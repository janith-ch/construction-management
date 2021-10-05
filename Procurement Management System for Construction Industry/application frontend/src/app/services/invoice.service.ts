import { Injectable } from '@angular/core';
import { JwtService } from './jwt.service';
import { MainApiService } from './main-api.service';

@Injectable({
  providedIn: 'root',
})
export class InvoiceService {
  constructor(
    private apiService: MainApiService,
    private jwtService: JwtService
  ) {}

  getAllInvoices() {
    return this.apiService.get('/api/v1/invoices');
  }

  addPayment(payment) {
    return this.apiService.post('/api/v1/payments', payment);
  }

  getAllPayment() {
    return this.apiService.get('/api/v1/payments');
  }
}
