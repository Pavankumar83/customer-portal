import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomerPhone, CustomerPhone } from 'app/shared/model/customer-phone.model';
import { CustomerPhoneService } from './customer-phone.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-customer-phone-update',
  templateUrl: './customer-phone-update.component.html',
})
export class CustomerPhoneUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    phoneNumber: [],
    extension: [],
    deleted: [],
    customer: [],
  });

  constructor(
    protected customerPhoneService: CustomerPhoneService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerPhone }) => {
      this.updateForm(customerPhone);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(customerPhone: ICustomerPhone): void {
    this.editForm.patchValue({
      id: customerPhone.id,
      phoneNumber: customerPhone.phoneNumber,
      extension: customerPhone.extension,
      deleted: customerPhone.deleted,
      customer: customerPhone.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerPhone = this.createFromForm();
    if (customerPhone.id !== undefined) {
      this.subscribeToSaveResponse(this.customerPhoneService.update(customerPhone));
    } else {
      this.subscribeToSaveResponse(this.customerPhoneService.create(customerPhone));
    }
  }

  private createFromForm(): ICustomerPhone {
    return {
      ...new CustomerPhone(),
      id: this.editForm.get(['id'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      extension: this.editForm.get(['extension'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerPhone>>): void {
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
