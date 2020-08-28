import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomerEmail, CustomerEmail } from 'app/shared/model/customer-email.model';
import { CustomerEmailService } from './customer-email.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-customer-email-update',
  templateUrl: './customer-email-update.component.html',
})
export class CustomerEmailUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    email: [],
    deleted: [],
    customer: [],
  });

  constructor(
    protected customerEmailService: CustomerEmailService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerEmail }) => {
      this.updateForm(customerEmail);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(customerEmail: ICustomerEmail): void {
    this.editForm.patchValue({
      id: customerEmail.id,
      email: customerEmail.email,
      deleted: customerEmail.deleted,
      customer: customerEmail.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerEmail = this.createFromForm();
    if (customerEmail.id !== undefined) {
      this.subscribeToSaveResponse(this.customerEmailService.update(customerEmail));
    } else {
      this.subscribeToSaveResponse(this.customerEmailService.create(customerEmail));
    }
  }

  private createFromForm(): ICustomerEmail {
    return {
      ...new CustomerEmail(),
      id: this.editForm.get(['id'])!.value,
      email: this.editForm.get(['email'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerEmail>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
