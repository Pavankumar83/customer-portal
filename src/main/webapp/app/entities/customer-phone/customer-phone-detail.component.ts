import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerPhone } from 'app/shared/model/customer-phone.model';

@Component({
  selector: 'jhi-customer-phone-detail',
  templateUrl: './customer-phone-detail.component.html',
})
export class CustomerPhoneDetailComponent implements OnInit {
  customerPhone: ICustomerPhone | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerPhone }) => (this.customerPhone = customerPhone));
  }

  previousState(): void {
    window.history.back();
  }
}
