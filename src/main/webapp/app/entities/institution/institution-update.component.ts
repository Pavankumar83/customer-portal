import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInstitution, Institution } from 'app/shared/model/institution.model';
import { InstitutionService } from './institution.service';

@Component({
  selector: 'jhi-institution-update',
  templateUrl: './institution-update.component.html',
})
export class InstitutionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    tradeName: [],
    taxNumber: [],
    url: [],
    deleted: [],
  });

  constructor(protected institutionService: InstitutionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ institution }) => {
      this.updateForm(institution);
    });
  }

  updateForm(institution: IInstitution): void {
    this.editForm.patchValue({
      id: institution.id,
      name: institution.name,
      tradeName: institution.tradeName,
      taxNumber: institution.taxNumber,
      url: institution.url,
      deleted: institution.deleted,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const institution = this.createFromForm();
    if (institution.id !== undefined) {
      this.subscribeToSaveResponse(this.institutionService.update(institution));
    } else {
      this.subscribeToSaveResponse(this.institutionService.create(institution));
    }
  }

  private createFromForm(): IInstitution {
    return {
      ...new Institution(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      tradeName: this.editForm.get(['tradeName'])!.value,
      taxNumber: this.editForm.get(['taxNumber'])!.value,
      url: this.editForm.get(['url'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstitution>>): void {
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
}
