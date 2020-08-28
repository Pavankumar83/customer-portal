import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerEmail } from 'app/shared/model/customer-email.model';

@Component({
  selector: 'jhi-customer-email-detail',
  templateUrl: './customer-email-detail.component.html',
})
export class CustomerEmailDetailComponent implements OnInit {
  customerEmail: ICustomerEmail | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerEmail }) => (this.customerEmail = customerEmail));
  }

  previousState(): void {
    window.history.back();
  }
}
