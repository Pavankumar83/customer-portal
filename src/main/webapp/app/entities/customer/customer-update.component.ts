import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { IInstitution } from 'app/shared/model/institution.model';
import { InstitutionService } from 'app/entities/institution/institution.service';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html',
})
export class CustomerUpdateComponent implements OnInit {
  isSaving = false;
  institutions: IInstitution[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    identificationType: [],
    customerType: [],
    deleted: [],
    institution: [],
  });

  constructor(
    protected customerService: CustomerService,
    protected institutionService: InstitutionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);

      this.institutionService.query().subscribe((res: HttpResponse<IInstitution[]>) => (this.institutions = res.body || []));
    });
  }

  updateForm(customer: ICustomer): void {
    this.editForm.patchValue({
      id: customer.id,
      name: customer.name,
      identificationType: customer.identificationType,
      customerType: customer.customerType,
      deleted: customer.deleted,
      institution: customer.institution,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      identificationType: this.editForm.get(['identificationType'])!.value,
      customerType: this.editForm.get(['customerType'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      institution: this.editForm.get(['institution'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>): void {
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

  trackById(index: number, item: IInstitution): any {
    return item.id;
  }
}
