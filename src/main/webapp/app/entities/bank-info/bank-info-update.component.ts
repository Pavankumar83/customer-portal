import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBankInfo, BankInfo } from 'app/shared/model/bank-info.model';
import { BankInfoService } from './bank-info.service';
import { IInstitution } from 'app/shared/model/institution.model';
import { InstitutionService } from 'app/entities/institution/institution.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

type SelectableEntity = IInstitution | ICustomer;

@Component({
  selector: 'jhi-bank-info-update',
  templateUrl: './bank-info-update.component.html',
})
export class BankInfoUpdateComponent implements OnInit {
  isSaving = false;
  institutions: IInstitution[] = [];
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    accountNumber: [null, [Validators.required]],
    accountHolder: [],
    branchCode: [],
    branchAddress: [],
    ifscCode: [],
    institution: [],
    customer: [],
  });

  constructor(
    protected bankInfoService: BankInfoService,
    protected institutionService: InstitutionService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankInfo }) => {
      this.updateForm(bankInfo);

      this.institutionService.query().subscribe((res: HttpResponse<IInstitution[]>) => (this.institutions = res.body || []));

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(bankInfo: IBankInfo): void {
    this.editForm.patchValue({
      id: bankInfo.id,
      name: bankInfo.name,
      accountNumber: bankInfo.accountNumber,
      accountHolder: bankInfo.accountHolder,
      branchCode: bankInfo.branchCode,
      branchAddress: bankInfo.branchAddress,
      ifscCode: bankInfo.ifscCode,
      institution: bankInfo.institution,
      customer: bankInfo.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bankInfo = this.createFromForm();
    if (bankInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.bankInfoService.update(bankInfo));
    } else {
      this.subscribeToSaveResponse(this.bankInfoService.create(bankInfo));
    }
  }

  private createFromForm(): IBankInfo {
    return {
      ...new BankInfo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      accountNumber: this.editForm.get(['accountNumber'])!.value,
      accountHolder: this.editForm.get(['accountHolder'])!.value,
      branchCode: this.editForm.get(['branchCode'])!.value,
      branchAddress: this.editForm.get(['branchAddress'])!.value,
      ifscCode: this.editForm.get(['ifscCode'])!.value,
      institution: this.editForm.get(['institution'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankInfo>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
