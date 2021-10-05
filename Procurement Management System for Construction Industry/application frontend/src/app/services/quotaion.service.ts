import { Injectable } from '@angular/core';
import { JwtService } from './jwt.service';
import { MainApiService } from './main-api.service';

@Injectable({
  providedIn: 'root',
})
export class QuotaionService {
  constructor(
    private apiService: MainApiService,
    private jwtService: JwtService
  ) {}

  getAllQuotations() {
    return this.apiService.get('/api/v1/quotations/orderList');
  }
  gerOrderByID(id) {
    return this.apiService.get('/api/v1/orders/' + id);
  }

  getQuotations() {
    return this.apiService.get('/api/v1/quotations/');
  }

  getQuotationDetails(id) {
    return this.apiService.get('/api/v1/quotations/' + id);
  }

  updateQuotaionStatus(id, status) {
    return this.apiService.put(
      '/api/v1/quotations/status',
      {},
      { id: id, status: status }
    );
  }

  updateOrderStatus(id, status) {
    return this.apiService.put(
      '/api/v1/orders/status',
      {},
      { id: id, status: status }
    );
  }
}
