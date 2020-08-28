import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomerAddress, CustomerAddress } from 'app/shared/model/customer-address.model';
import { CustomerAddressService } from './customer-address.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-customer-address-update',
  templateUrl: './customer-address-update.component.html',
})
export class CustomerAddressUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    address: [],
    town: [],
    city: [],
    zipCode: [],
    state: [],
    country: [],
    deleted: [],
    customer: [],
  });

  constructor(
    protected customerAddressService: CustomerAddressService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerAddress }) => {
      this.updateForm(customerAddress);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(customerAddress: ICustomerAddress): void {
    this.editForm.patchValue({
      id: customerAddress.id,
      address: customerAddress.address,
      town: customerAddress.town,
      city: customerAddress.city,
      zipCode: customerAddress.zipCode,
      state: customerAddress.state,
      country: customerAddress.country,
      deleted: customerAddress.deleted,
      customer: customerAddress.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerAddress = this.createFromForm();
    if (customerAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.customerAddressService.update(customerAddress));
    } else {
      this.subscribeToSaveResponse(this.customerAddressService.create(customerAddress));
    }
  }

  private createFromForm(): ICustomerAddress {
    return {
      ...new CustomerAddress(),
      id: this.editForm.get(['id'])!.value,
      address: this.editForm.get(['address'])!.value,
      town: this.editForm.get(['town'])!.value,
      city: this.editForm.get(['city'])!.value,
      zipCode: this.editForm.get(['zipCode'])!.value,
      state: this.editForm.get(['state'])!.value,
      country: this.editForm.get(['country'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerAddress>>): void {
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
