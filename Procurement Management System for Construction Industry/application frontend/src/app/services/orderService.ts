import { Injectable } from '@angular/core';
import { JwtService } from './jwt.service';
import { MainApiService } from './main-api.service';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor(
    private apiService: MainApiService,
    private jwtService: JwtService
  ) {}

  getAllOrders() {
    return this.apiService.get('/api/v1/orders');
  }

  addPayment(payment) {
    return this.apiService.post('/api/v1/payments', payment);
  }

  getAllPayment() {
    return this.apiService.get('/api/v1/payments');
  }

  getOrderByID(id) {
    return this.apiService.get('/api/v1/orders/' + id);
  }

  updateOrderStatus(id, status) {
    return this.apiService.put(
      '/api/v1/orders/status',
      {},
      { id: id, status: status }
    );
  }
}
