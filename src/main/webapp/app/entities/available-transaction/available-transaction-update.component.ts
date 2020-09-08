import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAvailableTransaction, AvailableTransaction } from 'app/shared/model/available-transaction.model';
import { AvailableTransactionService } from './available-transaction.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { IBankInfo } from 'app/shared/model/bank-info.model';
import { BankInfoService } from 'app/entities/bank-info/bank-info.service';

type SelectableEntity = ICustomer | IBankInfo;

@Component({
  selector: 'jhi-available-transaction-update',
  templateUrl: './available-transaction-update.component.html',
})
export class AvailableTransactionUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];
  bankinfos: IBankInfo[] = [];

  editForm = this.fb.group({
    id: [],
    transactionId: [null, []],
    transactionType: [null, [Validators.required]],
    transactionMode: [null, [Validators.required]],
    transAmount: [null, [Validators.required]],
    dateOfTransaction: [null, [Validators.required]],
    customer: [],
    account: [],
  });

  constructor(
    protected availableTransactionService: AvailableTransactionService,
    protected customerService: CustomerService,
    protected bankInfoService: BankInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ availableTransaction }) => {
      if (!availableTransaction.id) {
        const today = moment().startOf('day');
        availableTransaction.dateOfTransaction = today;
      }

      this.updateForm(availableTransaction);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));

      this.bankInfoService.query().subscribe((res: HttpResponse<IBankInfo[]>) => (this.bankinfos = res.body || []));
    });
  }

  updateForm(availableTransaction: IAvailableTransaction): void {
    this.editForm.patchValue({
      id: availableTransaction.id,
      transactionId: availableTransaction.transactionId,
      transactionType: availableTransaction.transactionType,
      transactionMode: availableTransaction.transactionMode,
      transAmount: availableTransaction.transAmount,
      dateOfTransaction: availableTransaction.dateOfTransaction ? availableTransaction.dateOfTransaction.format(DATE_TIME_FORMAT) : null,
      customer: availableTransaction.customer,
      account: availableTransaction.account,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const availableTransaction = this.createFromForm();
    if (availableTransaction.id !== undefined) {
      this.subscribeToSaveResponse(this.availableTransactionService.update(availableTransaction));
    } else {
      this.subscribeToSaveResponse(this.availableTransactionService.create(availableTransaction));
    }
  }

  private createFromForm(): IAvailableTransaction {
    return {
      ...new AvailableTransaction(),
      id: this.editForm.get(['id'])!.value,
      transactionId: this.editForm.get(['transactionId'])!.value,
      transactionType: this.editForm.get(['transactionType'])!.value,
      transactionMode: this.editForm.get(['transactionMode'])!.value,
      transAmount: this.editForm.get(['transAmount'])!.value,
      dateOfTransaction: this.editForm.get(['dateOfTransaction'])!.value
        ? moment(this.editForm.get(['dateOfTransaction'])!.value, DATE_TIME_FORMAT)
        : undefined,
      customer: this.editForm.get(['customer'])!.value,
      account: this.editForm.get(['account'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvailableTransaction>>): void {
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
