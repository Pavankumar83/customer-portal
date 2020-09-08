import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IReport, Report } from 'app/shared/model/report.model';
import { ReportService } from './report.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-report-update',
  templateUrl: './report-update.component.html',
})
export class ReportUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
    startPeriod: [null, [Validators.required]],
    endPeriod: [null, [Validators.required]],
    name: [null, [Validators.required]],
    generatedReportName: [null, [Validators.required]],
    generatedAIFName: [null, [Validators.required]],
    generatedReportLocation: [null, [Validators.required]],
    generatedAIFLocation: [],
    customer: [],
  });

  constructor(
    protected reportService: ReportService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ report }) => {
      if (!report.id) {
        const today = moment().startOf('day');
        report.startPeriod = today;
        report.endPeriod = today;
      }

      this.updateForm(report);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(report: IReport): void {
    this.editForm.patchValue({
      id: report.id,
      type: report.type,
      startPeriod: report.startPeriod ? report.startPeriod.format(DATE_TIME_FORMAT) : null,
      endPeriod: report.endPeriod ? report.endPeriod.format(DATE_TIME_FORMAT) : null,
      name: report.name,
      generatedReportName: report.generatedReportName,
      generatedAIFName: report.generatedAIFName,
      generatedReportLocation: report.generatedReportLocation,
      generatedAIFLocation: report.generatedAIFLocation,
      customer: report.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const report = this.createFromForm();
    if (report.id !== undefined) {
      this.subscribeToSaveResponse(this.reportService.update(report));
    } else {
      this.subscribeToSaveResponse(this.reportService.create(report));
    }
  }

  private createFromForm(): IReport {
    return {
      ...new Report(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      startPeriod: this.editForm.get(['startPeriod'])!.value
        ? moment(this.editForm.get(['startPeriod'])!.value, DATE_TIME_FORMAT)
        : undefined,
      endPeriod: this.editForm.get(['endPeriod'])!.value ? moment(this.editForm.get(['endPeriod'])!.value, DATE_TIME_FORMAT) : undefined,
      name: this.editForm.get(['name'])!.value,
      generatedReportName: this.editForm.get(['generatedReportName'])!.value,
      generatedAIFName: this.editForm.get(['generatedAIFName'])!.value,
      generatedReportLocation: this.editForm.get(['generatedReportLocation'])!.value,
      generatedAIFLocation: this.editForm.get(['generatedAIFLocation'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReport>>): void {
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
