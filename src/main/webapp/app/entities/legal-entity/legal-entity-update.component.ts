import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILegalEntity, LegalEntity } from 'app/shared/model/legal-entity.model';
import { LegalEntityService } from './legal-entity.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-legal-entity-update',
  templateUrl: './legal-entity-update.component.html',
})
export class LegalEntityUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    commercialName: [],
    taxNumber: [],
    title: [],
    dateOfStart: [],
    businessClosed: [],
    businessArea: [],
    deleted: [],
    customer: [],
  });

  constructor(
    protected legalEntityService: LegalEntityService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ legalEntity }) => {
      if (!legalEntity.id) {
        const today = moment().startOf('day');
        legalEntity.dateOfStart = today;
      }

      this.updateForm(legalEntity);

      this.customerService
        .query({ filter: 'legalentity-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomer[]) => {
          if (!legalEntity.customer || !legalEntity.customer.id) {
            this.customers = resBody;
          } else {
            this.customerService
              .find(legalEntity.customer.id)
              .pipe(
                map((subRes: HttpResponse<ICustomer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomer[]) => (this.customers = concatRes));
          }
        });
    });
  }

  updateForm(legalEntity: ILegalEntity): void {
    this.editForm.patchValue({
      id: legalEntity.id,
      commercialName: legalEntity.commercialName,
      taxNumber: legalEntity.taxNumber,
      title: legalEntity.title,
      dateOfStart: legalEntity.dateOfStart ? legalEntity.dateOfStart.format(DATE_TIME_FORMAT) : null,
      businessClosed: legalEntity.businessClosed,
      businessArea: legalEntity.businessArea,
      deleted: legalEntity.deleted,
      customer: legalEntity.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const legalEntity = this.createFromForm();
    if (legalEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.legalEntityService.update(legalEntity));
    } else {
      this.subscribeToSaveResponse(this.legalEntityService.create(legalEntity));
    }
  }

  private createFromForm(): ILegalEntity {
    return {
      ...new LegalEntity(),
      id: this.editForm.get(['id'])!.value,
      commercialName: this.editForm.get(['commercialName'])!.value,
      taxNumber: this.editForm.get(['taxNumber'])!.value,
      title: this.editForm.get(['title'])!.value,
      dateOfStart: this.editForm.get(['dateOfStart'])!.value
        ? moment(this.editForm.get(['dateOfStart'])!.value, DATE_TIME_FORMAT)
        : undefined,
      businessClosed: this.editForm.get(['businessClosed'])!.value,
      businessArea: this.editForm.get(['businessArea'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILegalEntity>>): void {
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
