import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INonWorkingDay, NonWorkingDay } from 'app/shared/model/non-working-day.model';
import { NonWorkingDayService } from './non-working-day.service';
import { IInstitution } from 'app/shared/model/institution.model';
import { InstitutionService } from 'app/entities/institution/institution.service';

@Component({
  selector: 'jhi-non-working-day-update',
  templateUrl: './non-working-day-update.component.html',
})
export class NonWorkingDayUpdateComponent implements OnInit {
  isSaving = false;
  institutions: IInstitution[] = [];

  editForm = this.fb.group({
    id: [],
    nonWorkingDay: [],
    deleted: [],
    institution: [],
  });

  constructor(
    protected nonWorkingDayService: NonWorkingDayService,
    protected institutionService: InstitutionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nonWorkingDay }) => {
      if (!nonWorkingDay.id) {
        const today = moment().startOf('day');
        nonWorkingDay.nonWorkingDay = today;
      }

      this.updateForm(nonWorkingDay);

      this.institutionService.query().subscribe((res: HttpResponse<IInstitution[]>) => (this.institutions = res.body || []));
    });
  }

  updateForm(nonWorkingDay: INonWorkingDay): void {
    this.editForm.patchValue({
      id: nonWorkingDay.id,
      nonWorkingDay: nonWorkingDay.nonWorkingDay ? nonWorkingDay.nonWorkingDay.format(DATE_TIME_FORMAT) : null,
      deleted: nonWorkingDay.deleted,
      institution: nonWorkingDay.institution,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nonWorkingDay = this.createFromForm();
    if (nonWorkingDay.id !== undefined) {
      this.subscribeToSaveResponse(this.nonWorkingDayService.update(nonWorkingDay));
    } else {
      this.subscribeToSaveResponse(this.nonWorkingDayService.create(nonWorkingDay));
    }
  }

  private createFromForm(): INonWorkingDay {
    return {
      ...new NonWorkingDay(),
      id: this.editForm.get(['id'])!.value,
      nonWorkingDay: this.editForm.get(['nonWorkingDay'])!.value
        ? moment(this.editForm.get(['nonWorkingDay'])!.value, DATE_TIME_FORMAT)
        : undefined,
      deleted: this.editForm.get(['deleted'])!.value,
      institution: this.editForm.get(['institution'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INonWorkingDay>>): void {
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
